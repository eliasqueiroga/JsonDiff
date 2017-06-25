package com.diff.controllers;

import com.diff.json.DiffResult;
import com.diff.json.InvalidJsonFormatException;
import com.diff.json.JSON;
import com.diff.json.JSONDiff;

import lef.server.services.Cache;

/**
 * 
 * 
 * @author elias
 *
 */
public class Controller {
	
	Cache cache = Cache.instance();
	
	JSONDiff getJsonDiff(String diffID){
		JSONDiff jsonDiff;
		if (cache.has(diffID)){
			jsonDiff = (JSONDiff)cache.get(diffID);			
		}else{
			jsonDiff = new JSONDiff();
			cache.set(diffID, jsonDiff);
		}
		
		return jsonDiff;
	}
	
	public void setLeftSide(String diffID, String json) throws InvalidJsonFormatException{
		JSONDiff jsonDiff = getJsonDiff(diffID); 
		
		jsonDiff.setLeft(JSON.parse(json));
	}
	
	public void setRightSide(String diffID, String json) throws InvalidJsonFormatException{
		JSONDiff jsonDiff = getJsonDiff(diffID); 
		
		jsonDiff.setRight(JSON.parse(json));
	}
	
	public DiffResult compare(String diffID){
		if (cache.has(diffID)){
			JSONDiff jsonDiff = getJsonDiff(diffID);
			return jsonDiff.compare();
		}else{
			return new DiffResult(DiffResult.Status.MISSING_BOTH_SIDES, "The ID " + diffID + " is not mapped");
		}
	}

}