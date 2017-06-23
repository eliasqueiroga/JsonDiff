package com.diff.jsons;

import static org.junit.Assert.*;

import org.junit.Test;

import com.diff.json.InvalidJsonFormatException;
import com.diff.json.JSON;

public class JSONTest {

	private JSON createJson(String jsonTxtFormat){
		try{
			return JSON.parse(jsonTxtFormat);
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
		JSON parsedJson = null;
		try {
			parsedJson = JSON.parse(json);
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
		JSON parsedJson = createJson(json);
		
		assertNotNull(parsedJson);
		
		assertEquals(parsedJson.toString(), json.replaceAll("\\s*", ""));
	}
	
	/**
	 * Comparing two equal JSONs
	 */
	@Test
	public void compareTwoJSONs() {
		String json = "{ 'field1' : 'Field1', 'field2' : ['a', 'b'] }";
		JSON parsedJson1 = createJson(json);
		
		JSON parsedJson2 = createJson(json);
		
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
		JSON parsedJson1 = createJson(json);
		
		JSON parsedJson2 = createJson(json2);
		
		assertNotNull(parsedJson1);
		
		assertNotNull(parsedJson2);
		
		assertNotEquals(parsedJson1, parsedJson2);
	}

}
