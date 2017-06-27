package com.diff.controllers;

import static org.junit.Assert.*;

import java.nio.charset.StandardCharsets;
import java.util.Random;

import org.junit.Test;

import com.diff.controllers.Controller;
import com.diff.json.JSONDiffResult;
import com.diff.json.InvalidJsonFormatException;
import com.diff.json.JSONDiff;
import com.diff.util.Util;

public class ControllerTest {
	
	
	/**
	 * Testing if the controller class is able to compare two equal json
	 * with the same size but different content.
	 */
	@Test
	public void compareSizeEqualButDifferentContentJsonTest() {
		Controller controller = new Controller();

		String diffID = "ID1";
		String leftJson = "{ 'field1' : 'Field1', 'field2' : ['a', 'b'] }";
		String rightJson = "{ 'field2' : 'Field2', 'field3' : ['a', 'b'] }";
		try {
			controller.setLeftSide(diffID, Util.base64Encoder(leftJson).getBytes(StandardCharsets.UTF_8));
			controller.setRightSide(diffID, Util.base64Encoder(rightJson).getBytes(StandardCharsets.UTF_8));
		} catch (InvalidJsonFormatException e) {
			fail("Invalid json format");
		}
		JSONDiffResult result = controller.compare(diffID);

		assertEquals(result.getStatus(), JSONDiffResult.Status.SIZE_EQUAL_WITH_DIFFERENT_CONTENT);
	}	

	/**
	 * Testing if the controller class is able to compare two equal json
	 * structures.
	 */
	@Test
	public void compareEqualJsonTest() {
		Controller controller = new Controller();

		String diffID = "ID1";
		String leftJson = "{ 'field1' : 'Field1', 'field2' : ['a', 'b'] }";
		String rightJson = "{ 'field1' : 'Field1', 'field2' : ['a', 'b'] }";
		try {
			controller.setLeftSide(diffID, Util.base64Encoder(leftJson).getBytes(StandardCharsets.UTF_8));
			controller.setRightSide(diffID, Util.base64Encoder(rightJson).getBytes(StandardCharsets.UTF_8));
		} catch (InvalidJsonFormatException e) {
			fail("Invalid json format");
		}
		JSONDiffResult result = controller.compare(diffID);

		assertEquals(result.getStatus(), JSONDiffResult.Status.SIZE_EQUAL);
	}

	/**
	 * Testing if the controller class is able to compare two different json
	 * structures.
	 */
	@Test
	public void compareDifferentJsonTest() {
		Controller controller = new Controller();

		String diffID = "ID1";
		String leftJson = "{ 'field1' : 'Field1', 'field2' : ['a', 'b'] }";
		String rightJson = "{ 'field1' : 'Field1', 'field2' : ['a', 'b'], 'field3' : false }";
		try {
			controller.setLeftSide(diffID, Util.base64Encoder(leftJson).getBytes(StandardCharsets.UTF_8));
			controller.setRightSide(diffID, Util.base64Encoder(rightJson).getBytes(StandardCharsets.UTF_8));
		} catch (InvalidJsonFormatException e) {
			fail("Invalid json format");
		}
		JSONDiffResult result = controller.compare(diffID);

		assertEquals(result.getStatus(), JSONDiffResult.Status.NOT_SIZE_EQUAL);
	}

	/**
	 * Create a new comparator by using an ID and check that it has no jsons to
	 * be compared.
	 */
	@Test
	public void registerNewDiffTest() {
		String leftJson = "{ 'field1' : 'Field1', 'field2' : ['a', 'b'] }";
		Controller controller = new Controller();
		String diffID = "ID_" + new Random().nextLong();
		JSONDiff diff = controller.getJsonDiff(diffID);
		try {
			controller.setLeftSide(diffID, Util.base64Encoder(leftJson).getBytes(StandardCharsets.UTF_8));
		} catch (InvalidJsonFormatException e) {
			fail("Invalid json format");
		}
		JSONDiff existingDiff = controller.getJsonDiff(diffID);

		assertNotNull(diff);

		JSONDiffResult result = diff.compare();

		assertEquals(result.getStatus(), JSONDiffResult.Status.MISSING_RIGHT_SIDE);
	}

	/**
	 * Get an existing comparator by using an ID and check that it has at least
	 * the left json.
	 */
	@Test
	public void getAnExistingDiffTest() {
		Controller controller = new Controller();
		String diffID = "ID_" + new Random().nextLong();
		JSONDiff diff = controller.getJsonDiff(diffID);

		assertNotNull(diff);

		JSONDiffResult result = diff.compare();

		assertEquals(result.getStatus(), JSONDiffResult.Status.MISSING_BOTH_SIDES);
	}

}
