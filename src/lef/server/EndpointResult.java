package lef.server;

import java.io.Serializable;

import com.google.gson.Gson;

public class EndpointResult implements Serializable{
	String status;
	Object content;
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Object getContent() {
		return content;
	}

	public void setContent(Object content) {
		this.content = content;
	}

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
	
	public static EndpointResult ENDPOINT_NOT_FOUND(){
		return new EndpointResult("Error", "Endpoint not found");
	}
	
	public static EndpointResult ENDPOINT_MAPPING_NOT_FOUND(){
		return new EndpointResult("Error", "Endpoint mapping was not found");
	}

	
	@Override
	public String toString() {
		return new Gson().toJson(this);
	}


}
