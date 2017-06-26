package com.diff.jsons;

import static org.junit.Assert.*;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import org.junit.Test;

import com.diff.json.InvalidJsonFormatException;
import com.diff.util.Util;
import com.sun.mail.iap.ByteArray;
import com.diff.json.BinaryJSON;

public class JSONTest {

	private BinaryJSON createJson(String jsonTxtFormat){
		try{
			return new BinaryJSON(Util.base64Encoder(jsonTxtFormat).getBytes(StandardCharsets.UTF_8));
			
		}catch(InvalidJsonFormatException e){
			fail("Invalid json format");
			throw new Error("Invalid json format");
		}
	}
	
	/**
	 * Create new JSON
	 */
	@Test
	public void parseInvalidJSON() {
		String json = "{ 'field1' : 'Field1', 'field2' :";
		BinaryJSON parsedJson = null;
		try {
			parsedJson = new BinaryJSON(Util.base64Encoder(json).getBytes(StandardCharsets.UTF_8));
		} catch (InvalidJsonFormatException e) {
			assertTrue(e instanceof InvalidJsonFormatException);
		}
	}	
	
	/**
	 * Create new JSON
	 */
	@Test
	public void createJSON() {
		String json = "{ 'field1' : 'Field1', 'field2' : ['a', 'b'] }";
		BinaryJSON parsedJson = createJson(json);
		
		assertNotNull(parsedJson);
		
		Arrays.equals(parsedJson.getBytes(), Util.base64Encoder(json).getBytes(StandardCharsets.UTF_8));
	}
	
	/**
	 * Comparing two equal JSONs
	 */
	@Test
	public void compareTwoJSONs() {
		String json = "{ 'field1' : 'Field1', 'field2' : ['a', 'b'] }";
		BinaryJSON parsedJson1 = createJson(json);
		
		BinaryJSON parsedJson2 = createJson(json);
		
		assertNotNull(parsedJson1);
		
		assertNotNull(parsedJson2);
		
		assertEquals(parsedJson1, parsedJson2);
	}
	
	/**
	 * Comparing two different JSONs
	 */
	@Test
	public void compareTwoDifferentJSONs() {
		String json = "{ 'field1' : 'Field1', 'field2' : ['a', 'b'] }";
		String json2 = "{ 'field1' : 'Field1', 'field2' : ['a', 'b'], 'field3' : false }";
		BinaryJSON parsedJson1 = createJson(json);
		
		BinaryJSON parsedJson2 = createJson(json2);
		
		assertNotNull(parsedJson1);
		
		assertNotNull(parsedJson2);
		
		assertNotEquals(parsedJson1, parsedJson2);
	}

}
