package com.diff.json;

public class JSON {
	
	private String json;
	
	public static JSON parse(String stringFormat) throws InvalidJsonFormatException{
		if (stringFormat != null){
			if (!stringFormat.startsWith("{") || !stringFormat.endsWith("}")){
				throw new InvalidJsonFormatException("The json informed is invalid format");
			}
		}else{
			throw new InvalidJsonFormatException("The json informed is invalid format");
		}
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
	
	@Override
	public String toString() {
		return this.json;
	}
}
