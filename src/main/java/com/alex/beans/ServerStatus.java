package com.alex.beans;

import java.io.Serializable;

public class ServerStatus implements Serializable {
	
	private String name, map, description, tags;
	private char operatingSystem;
	private int numOfPlayers,maxPlayers,numOfBots;
	private long appID;
	private long serverID;
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
	public char getOperatingSystem() {
		return operatingSystem;
	}
	public void setOperatingSystem(char operatingSystem) {
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
	public long getAppID() {
		return appID;
	}
	public void setAppID(long l) {
		this.appID = l;
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
	public long getServerID() {
		return serverID;
	}
	public void setServerID(long serverID) {
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
	
	public String toString() {
		return "name: " + this.name +
				",map: " + this.map +
				", desc: " + this.description + 
				", numPlayers: " + this.numOfPlayers + 
				", numBots: " + this.numOfBots +
				", appID: " + this.appID + 
				", serverID: " + this.serverID + 
				", isDedicated: " + this.isDedicated + 
				", isSecure: " + this.isSecure + 
				", operatingSystem: " + this.operatingSystem + 
				", tags: " + this.tags;
	}
	
}
