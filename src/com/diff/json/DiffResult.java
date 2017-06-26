package com.diff.json;

import java.util.List;

public class DiffResult {
	
	private Status status;
	private List<Integer> diffOffsets;
	
	public static enum Status {
		SIZE_EQUAL,
		SIZE_EQUAL_WITH_DIFFERENT_CONTENT,
		NOT_SIZE_EQUALS,
		MISSING_LEFT_SIDE,
		MISSING_RIGHT_SIDE,
		MISSING_BOTH_SIDES
	}


	public DiffResult(){
	}
	
	
	public DiffResult(Status status, List<Integer> diffOffsets){
		this.status = status;
		this.diffOffsets = diffOffsets;
	}
	
	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public List<Integer> getDetail() {
		return diffOffsets;
	}

	public void setDetail(List<Integer> diffOffsets) {
		this.diffOffsets = diffOffsets;
	}

}
