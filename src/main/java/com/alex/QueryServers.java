package com.alex;

import java.io.IOException;

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
    private static List<SourceServer> ssList;
    
    
	
	
		
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
    	SourceServer ss;
        final Map<String, Double> resultMap = new HashMap<>();

        final AtomicInteger masterServerCtr = new AtomicInteger(), masterError = new AtomicInteger();
        final AtomicInteger serverInfoCtr = new AtomicInteger(), serverInfoErr = new AtomicInteger();
        final AtomicInteger challengeCtr = new AtomicInteger(), challengeErr = new AtomicInteger();
        final AtomicInteger playersCtr = new AtomicInteger(), playersErr = new AtomicInteger();
        final AtomicInteger rulesCtr = new AtomicInteger(), rulesErr = new AtomicInteger();

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
                            QueryServers.appendSS(sourceServer);
                            
                        });

                        

                        //Get Challenge
                        
                        
                    } catch (Exception e) {
                        log.error("Error occured inside the master list callback", e);
                    }
                }).get(); //masterServerList

                System.out.println("Waiting for" + requestList.size() + " requests to complete");
                CompletableFuture[] futures = requestList.toArray(new CompletableFuture[0]);
                CompletableFuture.allOf(futures).get();
            } catch (Exception e) {
                log.error("Error occured during processing", e);
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
	
	public static void appendSS(SourceServer ss) {
		ssList.add(ss);
		System.out.println("whatever: " + ss);
	}
	
	
	
	
	

}
