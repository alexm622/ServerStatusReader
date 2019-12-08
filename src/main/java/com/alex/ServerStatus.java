package com.alex;

import java.io.Serializable;

public class ServerStatus implements Serializable {
	
	private String name, map, description, operatingSystem, tags;
	private int appID, numOfPlayers,maxPlayers,numOfBots, serverID;
	private boolean isDedicated, isSecure;
	
	public ServerStatus() {
		
	}
	//name 
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	//map
	public String getMap() {
		return map;
	}
	public void setMap(String map) {
		this.map = map;
	}
	
	//desc
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	//os
	public String getOperatingSystem() {
		return operatingSystem;
	}
	public void setOperatingSystem(String operatingSystem) {
		this.operatingSystem = operatingSystem;
	}
	
	//tags
	public String getTags() {
		return tags;
	}
	public void setTags(String tags) {
		this.tags = tags;
	}
	
	//appId
	public int getAppID() {
		return appID;
	}
	public void setAppID(int appID) {
		this.appID = appID;
	}
	
	//num of players
	public int getNumOfPlayers() {
		return numOfPlayers;
	}
	public void setNumOfPlayers(int numOfPlayers) {
		this.numOfPlayers = numOfPlayers;
	}
	
	//maxplayers
	public int getMaxPlayers() {
		return maxPlayers;
	}
	public void setMaxPlayers(int maxPlayers) {
		this.maxPlayers = maxPlayers;
	}
	
	//num of bots
	public int getNumOfBots() {
		return numOfBots;
	}
	public void setNumOfBots(int numOfBots) {
		this.numOfBots = numOfBots;
	}
	
	//server ID
	public int getServerID() {
		return serverID;
	}
	public void setServerID(int serverID) {
		this.serverID = serverID;
	}
	
	//is dedicated
	public boolean isDedicated() {
		return isDedicated;
	}
	public void setDedicated(boolean isDedicated) {
		this.isDedicated = isDedicated;
	}
	
	//is secure
	public boolean isSecure() {
		return isSecure;
	}
	public void setSecure(boolean isSecure) {
		this.isSecure = isSecure;
	}
	
}
