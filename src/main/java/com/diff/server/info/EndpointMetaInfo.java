package com.diff.server.info;

import java.util.HashMap;
import java.util.Map;

import com.diff.server.util.Util;

public class EndpointMetaInfo {

	private String[] paths;
	
	private String endpointName;

	private Map<String, String> pathParams;
	
	private Map<String, String> formParams;
	
	private Context context;
	
	public EndpointMetaInfo(String endpointPath, String... paths){
		init(endpointPath, paths);
		this.context = new Context();		
	}	
	
	public EndpointMetaInfo(String endpointPath, Context context, String... paths){
		init(endpointPath, paths);
		this.context = context;		
	}	

	private void init(String endpointPath, String... paths){
		this.endpointName = endpointPath;
		
		this.paths = paths;
		
		this.pathParams = new HashMap<String, String>();		
		this.formParams = new HashMap<String, String>();

	}

	public String getEndpointName() {
		return endpointName;
	}

	public void setEndpointName(String endpointName) {
		this.endpointName = endpointName;
	}
	
	public Context getContext() {
		return context;
	}
	
	public String[] getPaths() {
		return paths;
	}
	
	public Map<String, String> getPathParams(String endpointMapping){
		return Util.isMethodMapped(endpointMapping, paths); 
	}
	
	public Boolean matchMethodEndpoint(String endpointMapping, String requestMethod){
		boolean pathMatched = (getPathParams(endpointMapping) != null ? true : false);
		boolean requestMethodMatched = requestMethod.equalsIgnoreCase(context.getMethod());
		return pathMatched && requestMethodMatched; 
	}


	public Map<String, String> getFormParams(){
		return this.formParams; 
	}
}
