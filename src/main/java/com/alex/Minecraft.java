package com.alex;

import com.alex.beans.ServerStatus;
import com.alex.utils.MineStat;

public class Minecraft {
	public static ServerStatus mcServer() {
		ServerStatus status = new ServerStatus();
		MineStat ms = new MineStat("10.0.0.4", 25565);
		if(ms.isServerUp())
	    {
	     System.out.println("Server is online running version " + ms.getVersion() + " with " + ms.getCurrentPlayers() + " out of " + ms.getMaximumPlayers() + " players.");
	     System.out.println("Message of the day: " + ms.getMotd());
	     System.out.println("Latency: " + ms.getLatency() + "ms");
	     status = getStatus(ms);
	    }
	    else {
	      System.out.println("Server is offline!");
	    }
		return status;
	}
	
	private static ServerStatus getStatus(MineStat ms) {
		ServerStatus stat = new ServerStatus();
		
		stat.setDescription("the version is" + ms.getVersion());
		stat.setName(ms.getMotd());
		stat.setAppID(-1);
		stat.setMaxPlayers(Integer.parseInt(ms.getMaximumPlayers()));
		stat.setNumOfPlayers(Integer.parseInt(ms.getCurrentPlayers()));
		
		
		return stat;
	}
}
