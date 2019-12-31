package com.alex;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import com.alex.beans.ServerStatus;

public class WriteToSql {
	
	public WriteToSql() {
		
	}
	
	public static void write(ArrayList<ServerStatus>  ss) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			
			Connection con = DriverManager.getConnection("jdbc:mysql://10.0.0.6:3306/gameserver","sql", "thisisapassword!");
			
			BuildAndExecute(ss, con);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	private static void BuildAndExecute(ArrayList<ServerStatus> ssl, Connection con) throws Exception{
		String query ="delete from servervars;";
		PreparedStatement stmt = con.prepareStatement(query);
		stmt.execute();
		
		String statement = "insert into servervars values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
		
		
		
		for(ServerStatus ss : ssl) {
			stmt = con.prepareStatement(statement);
			stmt.setString(1, ss.getName());
			stmt.setString(2, ss.getMap());
			stmt.setString(3, ss.getDescription());
			stmt.setString(4, ss.getTags());
			stmt.setString(5, Character.toString(ss.getOperatingSystem()));
			stmt.setInt(6, ss.getNumOfBots());
			stmt.setInt(7, ss.getMaxPlayers());
			stmt.setInt(8, ss.getNumOfBots());
			stmt.setLong(9, ss.getAppID());
			stmt.setLong(10, ss.getServerID());
			stmt.setBoolean(11, ss.isDedicated());
			stmt.setBoolean(12, ss.isSecure());
			stmt.execute();
		}
		
	}
	
	
}
