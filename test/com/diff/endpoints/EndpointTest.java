package com.diff.endpoints;

import static org.junit.Assert.*;

import java.util.Random;

import org.junit.Test;

import com.diff.json.DiffResult;

public class EndpointTest {

	DiffEndpoint endpoint = new DiffEndpoint();
	
	@Test
	public void compareTwoEqualJsonTest() {
		String diffID = "ID_" + new Random().nextLong();		
		String json = "{ 'field1' : 'Field1', 'field2' : ['a', 'b'] }";
		String json2 = "{ 'field1' : 'Field1', 'field2' : ['a', 'b'] }";
		
		try {
			endpoint.setLeft(diffID,json);
			endpoint.setright(diffID, json2);
		} catch (Exception e) {
			fail("Invalid json format");
		}
		
		DiffResult result =  endpoint.getComparisonResult(diffID);
		
		assertEquals(result.getStatus(), "Both sides are equal");
		
	}

}
