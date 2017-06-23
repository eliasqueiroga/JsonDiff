package com.diff.controllers;

import com.diff.json.DiffResult;
import com.diff.json.JSON;
import com.diff.json.JSONDiff;

import lef.server.services.Cache;

public class Controller {
	
	Cache cache = Cache.instance();
	
	private JSONDiff getJsonDiff(String diffID){
		JSONDiff jsonDiff;
		if (cache.has(diffID)){
			jsonDiff = (JSONDiff)cache.get(diffID);			
		}else{
			jsonDiff = new JSONDiff();
			cache.set(diffID, jsonDiff);
		}
		
		return jsonDiff;
	}
	
	public void setLeftSide(String diffID, String json){
		JSONDiff jsonDiff = getJsonDiff(diffID); 
		
		jsonDiff.setLeft(JSON.parse(json));
	}
	
	public void setRightSide(String diffID, String json){
		JSONDiff jsonDiff = getJsonDiff(diffID); 
		
		jsonDiff.setRight(JSON.parse(json));
	}
	
	public DiffResult compare(String diffID){
		if (cache.has(diffID)){
			JSONDiff jsonDiff = getJsonDiff(diffID);
			return jsonDiff.compare();
		}else{
			return new DiffResult("Error", "The ID " + diffID + " is not mapped");
		}
		
		
		 
		
		
	}

}
