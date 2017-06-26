package com.diff.controllers;

import com.diff.json.JSONDiffResult;
import com.diff.json.InvalidJsonFormatException;
import com.diff.json.BinaryJSON;
import com.diff.json.JSONDiff;

import lef.server.services.Cache;

/**
 * Json Diff Controller class. It is responsible to receive all jsons
 * to be compared.
 * 
 * All Jsons data received are associated to unique ID and stored in a 
 * Key-Value storage where the key is the ID passed in the constructor method. 
 * 
 * @author Elias
 *
 */
public class Controller {
	
	/**
	 * An instance of memcached services provided by
	 * the Lef framework. It's a memory storage where the
	 * data is saved in a key-value structure. 
	 */
	Cache cacheService = Cache.instance();
	
	/**
	 * Returns an instance of JsonDiff associated
	 * to ID passed argument.
	 * 
	 * @param diffID The unique ID that is mapped to a existing JsonDiff
	 * @return An instance of JsonDiff associated to ID passed as argument
	 */
	JSONDiff getJsonDiff(String diffID){
		JSONDiff jsonDiff;
		if (cacheService.has(diffID)){
			jsonDiff = (JSONDiff)cacheService.get(diffID);			
		}else{
			jsonDiff = new JSONDiff();
			cacheService.set(diffID, jsonDiff);
		}
		
		return jsonDiff;
	}
	
	/**
	 * Sets the left json data to be compared
	 * 
	 * @param diffID The comparison ID
	 * @param jsonBase64 The json in base64 format
	 * @throws InvalidJsonFormatException
	 */
	public void setLeftSide(String diffID, byte[] jsonBase64) throws InvalidJsonFormatException{
		JSONDiff jsonDiff = getJsonDiff(diffID); 
		
		jsonDiff.setLeft(new BinaryJSON(jsonBase64));
	}
	
	/**
	 * Sets the left json data to be compared
	 * 
	 * @param diffID The comparison ID
	 * @param jsonBase64 The json in base64 format
	 * @throws InvalidJsonFormatException
	 */
	public void setRightSide(String diffID, byte[] jsonBase64) throws InvalidJsonFormatException{
		JSONDiff jsonDiff = getJsonDiff(diffID); 
		
		jsonDiff.setRight(new BinaryJSON(jsonBase64));
	}
	
	/**
	 * Gets the JsonDiff from memory storage by using the diff ID
	 * and compare them. As result, it returns an instance of DiffResult
	 * that gives more details of comparison.
	 * 
	 * @param diffID
	 * @return
	 */
	public JSONDiffResult compare(String diffID){
		if (cacheService.has(diffID)){
			JSONDiff jsonDiff = getJsonDiff(diffID);
			return jsonDiff.compare();
		}else{
			return new JSONDiffResult(JSONDiffResult.Status.MISSING_BOTH_SIDES, null);
		}
	}

}