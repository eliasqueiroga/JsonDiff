package com.diff.jsons;

import static org.junit.Assert.*;

import org.junit.Test;

import com.diff.json.DiffResult;
import com.diff.json.InvalidJsonFormatException;
import com.diff.json.JSON;
import com.diff.json.JSONDiff;

public class JSONDiffTest {

	/**
	 * Given two equal jsons, compare them and check they are equals.
	 */
	@Test
	public void compareEqualJsonsTest() {
		String json = "{ 'field1' : 'Field1', 'field2' : ['a', 'b'] }";
		JSON parsedJson1 = null;
		JSON parsedJson2 = null;
		try {
			parsedJson1 = JSON.parse(json);
			parsedJson2 = JSON.parse(json);
		} catch (InvalidJsonFormatException e) {
			fail("Invalid json");
		}

		JSONDiff jsonDiff = new JSONDiff();
		jsonDiff.setLeft(parsedJson1);
		jsonDiff.setRight(parsedJson2);

		DiffResult result = jsonDiff.compare();

		assertEquals(result.getStatus(), DiffResult.Status.EQUALS);
	}

	/**
	 * Given two different jsons, compare them and check they are not equals.
	 */
	@Test
	public void compareDifferentJsonsTest() {
		String json = "{ 'field1' : 'Field1', 'field2' : ['a', 'b'] }";
		String json2 = "{ 'field1' : 'Field1', 'field2' : ['a', 'b'], 'field3' : false }";
		JSON parsedJson1 = null;
		JSON parsedJson2 = null;

		try {
			parsedJson1 = JSON.parse(json);

			parsedJson2 = JSON.parse(json2);
		} catch (InvalidJsonFormatException e) {
			fail("Invalid json");
		}

		JSONDiff jsonDiff = new JSONDiff();
		jsonDiff.setLeft(parsedJson1);
		jsonDiff.setRight(parsedJson2);

		DiffResult result = jsonDiff.compare();

		assertEquals(result.getStatus(), DiffResult.Status.NOT_EQUALS);
	}

	/**
	 * Given two different jsons, compare them and check they are not equals.
	 */
	@Test
	public void tryCompareOneSideTest() {
		String json = "{ 'field1' : 'Field1', 'field2' : ['a', 'b'] }";
		String json2 = "{ 'field1' : 'Field1', 'field2' : ['a', 'b'], 'field3' : false }";
		JSON parsedJson1 = null;
		JSON parsedJson2 = null;
		try {
			parsedJson1 = JSON.parse(json);
			parsedJson2 = JSON.parse(json2);
		} catch (InvalidJsonFormatException e) {
			fail("Invalid json");
		}

		JSONDiff jsonDiff = new JSONDiff();
		jsonDiff.setLeft(parsedJson1);

		DiffResult result = jsonDiff.compare();

		assertEquals(result.getStatus(), DiffResult.Status.MISSING_RIGHT_SIDE);

		jsonDiff.setLeft(null);
		jsonDiff.setRight(parsedJson2);

		DiffResult result2 = jsonDiff.compare();

		assertEquals(result2.getStatus(), DiffResult.Status.MISSING_LEFT_SIDE);

	}

}
