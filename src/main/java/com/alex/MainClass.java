package com.alex;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.time.StopWatch;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;

import com.alex.beans.ServerStatus;
import com.ibasco.agql.protocols.valve.source.query.pojos.SourceServer;

public class MainClass {
	
	private static List<SourceServer> ssList;
	private static StopWatch sw;
	
	public static void main(String[] args) {
		
		update();
		sw = new StopWatch();
		sw.start();
		
		while(true) {
			if(sw.getTime() >= (long) 60000) {
				sw.stop();
				update();
				sw.reset();
				sw.start();
			}else {
				long time = sw.getTime();
				long time2 = 60000 - time;
				try {
					System.out.println("Sleeping for " + time2 + " millis");
					Thread.sleep(time2);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		
		
    	
    	
    }
	
	public static void tryReading() throws IOException, ClassNotFoundException{
		ArrayList<ServerStatus> ss;
		File file = new File("/tmp/servervars/serverstatus.bin");
		FileInputStream fis = new FileInputStream(file);
		ObjectInputStream ois = new ObjectInputStream(fis);
		ss = (ArrayList<ServerStatus>) ois.readObject();
		ois.close();
		fis.close();
		
		
		for(ServerStatus s : ss) {
			System.out.println(s);
		}
		
		WriteToSql.write(ss);
	}
	
	private static void update() {
		QueryServers q = new QueryServers();
    	try {
			q.run();
			Thread.sleep(5000);
			System.out.println("updating");
			
			
			//get the current server status and delete last config
			File f = new File("/tmp/servervars/serverstatus.bin");
			

			
			
			//rename the new file to represent the readable file
			File f2 = new File("/tmp/servervars/serverstatus.tmp");
			
			f2.renameTo(f);
			
			Thread.sleep(5000);
			
			tryReading();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
	}
	
	
}
