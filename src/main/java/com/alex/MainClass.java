package com.alex;

public class MainClass {
	public static void main(String[] args) {
    	QueryServers q = new QueryServers();
    	try {
			q.run();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }
}
