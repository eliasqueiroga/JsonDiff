package com.diff.jsons;

import static org.junit.Assert.*;

import java.nio.charset.StandardCharsets;

import org.junit.Test;

import com.diff.json.DiffResult;
import com.diff.json.InvalidJsonFormatException;
import com.diff.json.BinaryJSON;
import com.diff.json.JSONDiff;
import com.diff.util.Util;

public class JSONDiffTest {

	/**
	 * Given two equal jsons, compare them and check they are equals.
	 */
	@Test
	public void compareEqualJsonsTest() {
		String json = "{ 'field1' : 'Field1', 'field2' : ['a', 'b'] }";
		BinaryJSON parsedJson1 = null;
		BinaryJSON parsedJson2 = null;
		try {
			parsedJson1 = new BinaryJSON(Util.base64Encoder(json).getBytes(StandardCharsets.UTF_8));
			parsedJson2 = new BinaryJSON(Util.base64Encoder(json).getBytes(StandardCharsets.UTF_8));
		} catch (InvalidJsonFormatException e) {
			fail("Invalid json");
		}

		JSONDiff jsonDiff = new JSONDiff();
		jsonDiff.setLeft(parsedJson1);
		jsonDiff.setRight(parsedJson2);

		DiffResult result = jsonDiff.compare();

		assertEquals(result.getStatus(), DiffResult.Status.SIZE_EQUAL);
	}
	
	/**
	 * Given two jsons with equal sizes, compare them and check they are equals.
	 * However, they have a difference in their content.
	 */
	@Test
	public void compareSuzeEqualDifferentContentJsonsTest() {
		String json = "{ 'field1' : 'Field1', 'field2' : ['a', 'b'] }";
		String json2 = "{ 'field2' : 'Field1', 'field2' : ['G', 'D'] }";
		BinaryJSON parsedJson1 = null;
		BinaryJSON parsedJson2 = null;
		try {
			parsedJson1 = new BinaryJSON(Util.base64Encoder(json).getBytes(StandardCharsets.UTF_8));
			parsedJson2 = new BinaryJSON(Util.base64Encoder(json2).getBytes(StandardCharsets.UTF_8));
		} catch (InvalidJsonFormatException e) {
			fail("Invalid json");
		}

		JSONDiff jsonDiff = new JSONDiff();
		jsonDiff.setLeft(parsedJson1);
		jsonDiff.setRight(parsedJson2);

		DiffResult result = jsonDiff.compare();

		assertEquals(result.getStatus(), DiffResult.Status.SIZE_EQUAL_WITH_DIFFERENT_CONTENT);
	}	

	/**
	 * Given two different jsons, compare them and check they are not equals.
	 */
	@Test
	public void compareDifferentJsonsTest() {
		String json = "{ 'field1' : 'Field1', 'field2' : ['a', 'b'] }";
		String json2 = "{ 'field1' : 'Field1', 'field2' : ['a', 'b'], 'field3' : false }";
		BinaryJSON parsedJson1 = null;
		BinaryJSON parsedJson2 = null;

		try {
			parsedJson1 = new BinaryJSON(Util.base64Encoder(json).getBytes(StandardCharsets.UTF_8));

			parsedJson2 = new BinaryJSON(Util.base64Encoder(json2).getBytes(StandardCharsets.UTF_8));
		} catch (InvalidJsonFormatException e) {
			fail("Invalid json");
		}

		JSONDiff jsonDiff = new JSONDiff();
		jsonDiff.setLeft(parsedJson1);
		jsonDiff.setRight(parsedJson2);

		DiffResult result = jsonDiff.compare();

		assertEquals(result.getStatus(), DiffResult.Status.NOT_SIZE_EQUALS);
	}

	/**
	 * Given two different jsons, compare them and check they are not equals.
	 */
	@Test
	public void tryCompareOneSideTest() {
		String json = "{ 'field1' : 'Field1', 'field2' : ['a', 'b'] }";
		String json2 = "{ 'field1' : 'Field1', 'field2' : ['a', 'b'], 'field3' : false }";
		BinaryJSON parsedJson1 = null;
		BinaryJSON parsedJson2 = null;
		try {
			parsedJson1 = new BinaryJSON(Util.base64Encoder(json).getBytes(StandardCharsets.UTF_8));
			parsedJson2 = new BinaryJSON(Util.base64Encoder(json2).getBytes(StandardCharsets.UTF_8));
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
