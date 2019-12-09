package com.alex;

import java.util.List;

import com.ibasco.agql.protocols.valve.source.query.pojos.SourceServer;

public class MainClass {
	
	private static List<SourceServer> ssList;
	
	public static void main(String[] args) {
		
		
		
		
    	QueryServers q = new QueryServers();
    	try {
			q.run();
			//Thread.sleep(4000);
			//System.out.println(QueryServers.ss + " line whatever");
			/*
			for(int i = 0; i < QueryServers.ssList.size(); i++) {
				System.out.println(QueryServers.ssList.get(i));
			}*/
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }
}
