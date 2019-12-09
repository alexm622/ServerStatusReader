package com.alex;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
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

import com.ibasco.agql.protocols.valve.source.query.pojos.SourceServer;

public class MainClass {
	
	private static List<SourceServer> ssList;
	private static StopWatch sw;
	
	public static void main(String[] args) {
		
		update();
		sw.start();
		
		while(true) {
			if(sw.getTime() >= 600000) {
				sw.reset();
				update();
			}else {
				long time = sw.getTime();
				time = 600000 - time;
				try {
					System.out.println("Sleeping for " + time + " millis");
					Thread.sleep(time);
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
	}
	
	private static void update() {
		QueryServers q = new QueryServers();
    	try {
			q.run();
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
	}
	
	
}
