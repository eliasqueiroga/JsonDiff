package com.diff.controllers;

import static org.junit.Assert.*;

import java.nio.charset.StandardCharsets;
import java.util.Random;

import org.junit.Test;

import com.diff.controllers.Controller;
import com.diff.json.DiffResult;
import com.diff.json.InvalidJsonFormatException;
import com.diff.json.JSONDiff;
import com.diff.util.Util;

public class ControllerTest {
	
	
	/**
	 * Testing if the controller class is able to compare two equal json
	 * structures.
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
		DiffResult result = controller.compare(diffID);

		assertEquals(result.getStatus(), DiffResult.Status.SIZE_EQUAL_WITH_DIFFERENT_CONTENT);
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
		DiffResult result = controller.compare(diffID);

		assertEquals(result.getStatus(), DiffResult.Status.SIZE_EQUAL);
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
		DiffResult result = controller.compare(diffID);

		assertEquals(result.getStatus(), DiffResult.Status.NOT_SIZE_EQUALS);
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

		DiffResult result = diff.compare();

		assertEquals(result.getStatus(), DiffResult.Status.MISSING_RIGHT_SIDE);
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

		DiffResult result = diff.compare();

		assertEquals(result.getStatus(), DiffResult.Status.MISSING_BOTH_SIDES);
	}

}
