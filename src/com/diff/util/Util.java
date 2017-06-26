package com.diff.util;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * Class with methods to support the project. It contains methods
 * to convert values in different formats and other stuffs.
 * 
 * @author Elias
 *
 */
public class Util {	

	/**
	 * Encode a simple String to base64 format.
	 * 
	 * @param word Value in string formar
	 * @return String with value converted to Base64 format.
	 */
	public static String base64Encoder(String word) {
        final byte[] wordBytes = word.getBytes(StandardCharsets.UTF_8);
        return Base64.getEncoder().encodeToString(wordBytes);        
	}
	
	/**
	 * Decode a string in base64 format to a text format.
	 * 
	 * @param binaryData String encoded in base64 format.
	 * @return text in String utf8 format.
	 */
	public static String base64Decoder(byte[] binaryData) {
        return new String(Base64.getDecoder().decode(binaryData));        
	}	

}
