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

import com.ibasco.agql.protocols.valve.source.query.pojos.SourceServer;

public class MainClass {
	
	private static List<SourceServer> ssList;
	
	public static void main(String[] args) {
		
		
		
		
    	QueryServers q = new QueryServers();
    	try {
			q.run();
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	try {
    		Thread.sleep(5000);
			tryReading();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
	
	
}
