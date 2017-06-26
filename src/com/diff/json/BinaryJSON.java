package com.diff.json;

import com.diff.util.Util;

public class BinaryJSON {
	
	private byte[] json;
		
	public BinaryJSON(byte[] binaryJson) throws InvalidJsonFormatException{
		this.json = binaryJson;
		
		if (!isValidJson()){
			throw new InvalidJsonFormatException("The json is not valid");
		}
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj != null){
			if (obj instanceof BinaryJSON){
				return this.json.length == ((BinaryJSON)obj).json.length;
			}else{
				return false;
			}
		}else{
			return false;
		}	
	}
	
	/**
	 * This method only checks if the binary received in constructor
	 * methos is a json value. It needs to be improved to make a solid
	 * verification instead of checking only brackets presence.
	 * 
	 * @return True if the current json is a json format and false 
	 * otherwise
	 */
	private boolean isValidJson(){
		String jsonTxt = Util.base64Decoder(this.json);
		
		if (jsonTxt.startsWith("{") && jsonTxt.endsWith("}")){
			return true;
		}else{
			return false;
		}
	}
	
	public byte[] getBytes(){
		return json;
	}
}
