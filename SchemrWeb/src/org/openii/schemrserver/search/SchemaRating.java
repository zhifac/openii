package org.openii.schemrserver.search;

import java.net.InetAddress;
import java.util.HashMap;

public class SchemaRating {
	//Global HashMap mapping Schemas to SchemaRatings
	public static HashMap<Integer, SchemaRating> ratings = new HashMap<Integer, SchemaRating>();
	//Number of each rating
	private int[] stars = new int[6];
	//IP Address to rating map
	private HashMap<InetAddress, Integer> ipMap = new HashMap<InetAddress, Integer>();  
	public SchemaRating(Integer schemaID){ //Only call constructor for new ratings
		for (int i=0; i < 6; i++) stars[i]=0; //Initialize ratings
		ratings.put(schemaID, this); //Add to global hashmap
	}
	public int numRatings(){
		return stars[1] + stars[2] + stars[3] + stars[4] + stars[5];
	}
	public double avgRating(){
		return (1*stars[1] + 2*stars[2] + 3*stars[3] + 4*stars[4] + 5*stars[5]) / numRatings();
	}
	public boolean rate(Integer rating, InetAddress ip){
		if (ipMap.containsKey(ip)) return false;
		ipMap.put(ip, rating);
		stars[rating]++;
		return true;
	}
}
