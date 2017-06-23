package com.diff.server;

import java.io.Serializable;

public class EndpointResult implements Serializable{
	String status;
	Object content;
	
	private EndpointResult(String status, Object content){
		this.content = content;
		this.status = status;
	}
	
	public static EndpointResult OK(Object result){
		return new EndpointResult("Success", result);
	}
	
	public static EndpointResult ERROR(Object result){
		return new EndpointResult("Error", result);
	}
	
	public static EndpointResult _404(){
		return new EndpointResult("Error", "Endpoint not found");
	}


}
