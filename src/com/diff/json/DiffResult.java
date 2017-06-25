package com.diff.json;

public class DiffResult {
	
	private Status status;
	private String detail;
	
	public static enum Status {
		EQUALS,
		NOT_EQUALS,
		MISSING_LEFT_SIDE,
		MISSING_RIGHT_SIDE,
		MISSING_BOTH_SIDES
	}


	public DiffResult(){
	}
	
	
	public DiffResult(Status status, String detail){
		this.status = status;
		this.detail = detail;
	}
	
	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

}
