package com.alex;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;

import com.ibasco.agql.core.utils.ConcurrentUtils;
import com.ibasco.agql.protocols.valve.source.query.client.SourceQueryClient;
import com.ibasco.agql.protocols.valve.source.query.enums.SourceChallengeType;
import com.ibasco.agql.protocols.valve.source.query.pojos.SourcePlayer;
import com.ibasco.agql.protocols.valve.source.query.pojos.SourceServer;
import com.ibasco.agql.protocols.valve.steam.master.MasterServerFilter;
import com.ibasco.agql.protocols.valve.steam.master.client.MasterServerQueryClient;
import com.ibasco.agql.protocols.valve.steam.master.enums.MasterServerRegion;
import com.ibasco.agql.protocols.valve.steam.master.enums.MasterServerType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicInteger;


public class QueryServers extends BaseExample{
	
	private static final Logger log = LoggerFactory.getLogger(QueryServers.class);
    private SourceQueryClient sourceQueryClient;
    private MasterServerQueryClient masterServerQueryClient;
    public static ArrayList<ServerStatus> ss;
    
    
	
	
		
	public void queryAllServers() {
       

        MasterServerFilter filter = MasterServerFilter.create()
        		.hasServerIp("73.17.34.121");
                

        

        System.out.println("Using filter:" + filter.toString() );

        double start = System.currentTimeMillis();
        queryAllServers(filter);
        
        double end = ((System.currentTimeMillis() - start) / 1000) / 60;
        System.out.println("Test Completed  in" + end +  "minutes");
        
        
    }

    private SourceServer queryAllServers(MasterServerFilter filter) {
        final Map<String, Double> resultMap = new HashMap<>();

        final AtomicInteger masterServerCtr = new AtomicInteger(), masterError = new AtomicInteger();
        final AtomicInteger serverInfoCtr = new AtomicInteger(), serverInfoErr = new AtomicInteger();
        final AtomicInteger challengeCtr = new AtomicInteger(), challengeErr = new AtomicInteger();
        final AtomicInteger playersCtr = new AtomicInteger(), playersErr = new AtomicInteger();
        final AtomicInteger rulesCtr = new AtomicInteger(), rulesErr = new AtomicInteger();
        
        ss = new ArrayList<ServerStatus>();
        

        try {
            List<CompletableFuture<?>> requestList = new ArrayList<>();

            try {
                masterServerQueryClient.getServerList(MasterServerType.SOURCE, MasterServerRegion.REGION_ALL, filter, (serverAddress, masterServerSender, masterServerError) -> {
                    try {
                        if (masterServerError != null) {
                        	System.out.println("[MASTER : ERROR] :  From:" + masterServerSender + "=" + masterServerError.getMessage());
                            masterError.incrementAndGet();
                            return;
                        }
                        

                        System.out.println("[MASTER : INFO line 75] : " + serverAddress);
                        masterServerCtr.incrementAndGet();

                        CompletableFuture<SourceServer> infoFuture = sourceQueryClient.getServerInfo(serverAddress).whenComplete((sourceServer, serverInfoError) -> {
                            if (serverInfoError != null) {
                            	System.out.println("[SERVER : ERROR] :" + serverInfoError.getMessage());
                                serverInfoErr.incrementAndGet();
                                return;
                            }
                            serverInfoCtr.incrementAndGet();
                            
                            System.out.println("[SERVER : INFO] line 85 : " + sourceServer);
                            System.out.println("gameid: " + sourceServer.getGameId());
                            
                            ServerStatus status = convertToBean(sourceServer);
                            
                            ss.add(status);
                            
 
                            
                            try {
								writeBeans(ss);
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
                            
                            
                        });
                        
                        
                        
                        //Get Challenge
                        
                        
                    } catch (Exception e) {
                        log.error("Error occured inside the master list callback", e);
                    }
                }).get(); //masterServerList
                
                
                System.out.println("Waiting for " + requestList.size() + " requests to complete");
                CompletableFuture[] futures = requestList.toArray(new CompletableFuture[0]);
                CompletableFuture.allOf(futures).get();
                
                
            } catch (Exception e) {
                e.printStackTrace();
            } 
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        
        return null;
    }
	
	@Override
	public void close() throws IOException {
		// TODO Auto-generated method stub
		System.out.println("Closing");
        sourceQueryClient.close();
        masterServerQueryClient.close();
	}
	@Override
	public void run() throws Exception {
		// TODO Auto-generated method stub
		sourceQueryClient = new SourceQueryClient();
        masterServerQueryClient = new MasterServerQueryClient();

        System.out.println("NOTE: Depending on your selected criteria, the application may time some time to complete. You can review the log file(s) once the program exits.");

        ConcurrentUtils.sleepUninterrupted(5000);

        this.queryAllServers();
	}
	
	
	private static ServerStatus convertToBean(SourceServer ss) {
		ServerStatus stat = new ServerStatus();
		
		stat.setName(ss.getName());
		stat.setMap(ss.getMapName());
		stat.setDescription(ss.getGameDescription());
		stat.setNumOfPlayers(ss.getNumOfPlayers());
		stat.setNumOfBots(ss.getNumOfBots());
		stat.setOperatingSystem(ss.getOperatingSystem());
		stat.setAppID(ss.getGameId());
		stat.setDedicated(ss.isDedicated());
		stat.setSecure(ss.isSecure());
		stat.setServerID(ss.getServerId());
		stat.setTags(ss.getServerTags());
		
		return stat;
	}
	
	private static void writeBeans(ArrayList<ServerStatus> ss) throws IOException{
		
		File file = new File("/tmp/servervars/");
		if(!file.exists()) {
			file.mkdirs();
			
		}
		file = new File("/tmp/servervars/serverstatus.tmp");
		if(!file.exists()) {
			file.createNewFile();
		}
		FileOutputStream f = new FileOutputStream(file);
		ObjectOutputStream o  = new ObjectOutputStream(f);
		
		
		
		o.writeObject(ss);
		o.close();
		f.close();
		
	}
	
	
	
	
	
	
	
	

}
