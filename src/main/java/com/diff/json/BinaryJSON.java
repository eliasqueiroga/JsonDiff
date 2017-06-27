package com.diff.json;

import com.diff.util.Util;

/**
 * This class represents a binary json format. Basically, it receives a json
 * data in binary format in the constructor method that makes a initial
 * verification if the binary represents a json data.
 * 
 * The equals method is implemented comparing if the size is equal.
 * 
 * @author Elias
 *
 */
public class BinaryJSON {

	private byte[] json;

	/**
	 * Constructor method that receives a json content in binary format.
	 * 
	 * @param binaryJson
	 * @throws InvalidJsonFormatException
	 */
	public BinaryJSON(byte[] binaryJson) throws InvalidJsonFormatException {
		this.json = binaryJson;

		try {
			if (!isValidJson()) {
				throw new InvalidJsonFormatException("The json is not valid");
			}
		} catch (Exception e) {
			throw new InvalidJsonFormatException("The json is not valid");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj != null) {
			if (obj instanceof BinaryJSON) {
				return this.json.length == ((BinaryJSON) obj).json.length;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	/**
	 * This method only checks if the binary received in constructor methos is a
	 * json value. It needs to be improved to make a solid verification instead
	 * of checking only brackets presence.
	 * 
	 * @return True if the current json is a json format and false otherwise
	 */
	private boolean isValidJson() {
		String jsonTxt = Util.base64Decoder(this.json);

		if (jsonTxt.startsWith("{") && jsonTxt.endsWith("}")) {
			return true;
		} else {
			return false;
		}
	}

	public byte[] getBytes() {
		return json;
	}
}
