package com.alex;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;



public class Timing {
	private static DateTimeFormatter dtf;
	private static Pair<Boolean, Integer> result;
	public Timing() {
		
		
	}
	
	
	public static Pair<Boolean, Integer> shouldUpdate() {
		
		//set date format
		dtf = DateTimeFormatter.ofPattern("mm");
		
		//get current time
		LocalDateTime now = LocalDateTime.now();
		
		//format the string
		String time = dtf.format(now);
		
		//convert to an integer
		int minutes = Integer.parseInt(time);
		
		//test if it is divisible by 10
		Boolean updateable = (minutes % 10) == 0 ? true : false;
		
		//calculate time left until next refresh time
		int timeleft = 10 - (minutes % 10);
		
		//create return pair
		result = new MutablePair<>(updateable, timeleft);
		
		//return
		return result;
	}
	
	

}
