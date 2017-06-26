package com.diff.json;

import java.util.List;

/**
 * This class represents the json comparison result. It is used by JSONDiff class that
 * is able to compare two jsons in binary format. 
 * 
 * It provides two information: status and detail. 
 *    - Status: Informs if the comparison action has been performed successfully.
 *    - offsets: A list of integers that represents the offsets where the differences are.  
 * 
 * @author Elias
 */
public class JSONDiffResult {
	
	private Status status;
	private List<Integer> offsets;
	
	public static enum Status {
		SIZE_EQUAL,
		SIZE_EQUAL_WITH_DIFFERENT_CONTENT,
		NOT_SIZE_EQUAL,
		MISSING_LEFT_SIDE,
		MISSING_RIGHT_SIDE,
		MISSING_BOTH_SIDES
	}


	public JSONDiffResult(){
		super();
	}
	
	
	public JSONDiffResult(Status status, List<Integer> diffOffsets){
		this.status = status;
		this.offsets = diffOffsets;
	}
	
	public Status getStatus() {
		return status;
	}

	public List<Integer> getDetail() {
		return offsets;
	}
}
