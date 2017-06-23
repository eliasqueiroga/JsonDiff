package com.diff.json;

public class JSON {
	
	private String json;
	
	public static JSON parse(String stringFormat){
		return new JSON(stringFormat.replaceAll("\\s*", ""));
	}
	
	private JSON(String textFormat){
		this.json = textFormat;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj != null){
			if (obj instanceof JSON){
				return this.json.equalsIgnoreCase(((JSON)obj).json);
			}else{
				return false;
			}
		}else{
			return false;
		}	
	}
}
