package com.diff.endpoints;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.Random;

import org.junit.Assert;
import org.junit.Test;

import com.diff.JsonUtil;
import com.diff.json.JSONDiffResult;
import com.diff.util.Util;

import lef.server.exception.BadRequestException;

public class EndpointTest {

	DiffEndpoint endpoint = new DiffEndpoint();


	/**
	 * Comparing two equal jsons.
	 */
	@Test
	public void compareTwoEqualJsonTest() {
		String diffID = "ID_" + new Random().nextLong();
		String[] jsons = JsonUtil.getJsons(true, true, true);
		String json = jsons[0];
		String json2 = jsons[1];

		try {
			endpoint.setLeft(diffID, json);
			endpoint.setright(diffID, json2);
		} catch (Exception e) {
			fail("Invalid json format");
		}

		JSONDiffResult result = endpoint.getComparisonResult(diffID);

		assertEquals(result.getStatus(), JSONDiffResult.Status.SIZE_EQUAL);
	}

	/**
	 * Comparing two different jsons.
	 */
	@Test
	public void compareTwoDifferentJsonTest() {
		String diffID = "ID_" + new Random().nextLong();
		String[] jsons = JsonUtil.getJsons(true, false, true);
		String json = jsons[0];
		String json2 = jsons[1];

		try {
			endpoint.setLeft(diffID, json);
			endpoint.setright(diffID, json2);
		} catch (Exception e) {
			fail("Invalid json format");
		}

		JSONDiffResult result = endpoint.getComparisonResult(diffID);

		assertEquals(result.getStatus(), JSONDiffResult.Status.NOT_SIZE_EQUAL);
	}

	/**
	 * Comparing no jsons.
	 */
	@Test
	public void compareNoJsonTest() {
		String diffID = "ID_" + new Random().nextLong();
		String[] jsons = JsonUtil.getJsons(false, false, true);

		JSONDiffResult result = endpoint.getComparisonResult(diffID);

		assertEquals(result.getStatus(), JSONDiffResult.Status.MISSING_BOTH_SIDES);
	}

	/**
	 * Comparing jsons with null ID.
	 */
	@Test
	public void compareWrongIDTest() {
		String[] jsons = JsonUtil.getJsons(false, false, true);
		String json = jsons[0];
		String json2 = jsons[1];

		try {
			endpoint.setLeft(null, json);
		} catch (Exception e) {
			if (e instanceof BadRequestException) {
				Assert.assertTrue(true);
			} else {
				fail();
			}
		}

		try {
			endpoint.setright(null, json);
		} catch (Exception e) {
			if (e instanceof BadRequestException) {
				Assert.assertTrue(true);
			} else {
				fail();
			}
		}
	}

	/**
	 * Trying to set a json with no base64 format
	 */
	@Test
	public void trySetNoBase64JsonTest() {
		String diffID = "ID_" + new Random().nextLong();
		String[] jsons = JsonUtil.getJsons(false, true, true);
		String json = jsons[0];

		try {
			endpoint.setLeft(diffID, json);
		} catch (Exception e) {
			if (e instanceof BadRequestException) {
				Assert.assertTrue(true);
			} else {
				fail();
			}
		}

		JSONDiffResult result = endpoint.getComparisonResult(diffID);

		assertEquals(result.getStatus(), JSONDiffResult.Status.MISSING_BOTH_SIDES);
	}

	/**
	 * Set only left side and try to compare
	 */
	@Test
	public void tryToCompareOnlyLeftTest() {
		String diffID = "ID_" + new Random().nextLong();
		String[] jsons = JsonUtil.getJsons(true, true, true);
		String json = jsons[0];

		try {
			endpoint.setLeft(diffID, json);
		} catch (Exception e) {
			fail();
		}

		JSONDiffResult result = endpoint.getComparisonResult(diffID);

		assertEquals(result.getStatus(), JSONDiffResult.Status.MISSING_RIGHT_SIDE);
	}

	/**
	 * Try to compare Null jsons
	 */
	@Test
	public void tryToCompareNullJsonTest() {
		String diffID = "ID_" + new Random().nextLong();
		String json1 = null;
		String json2 = null;

		try {
			endpoint.setLeft(diffID, json1);
			endpoint.setright(diffID, json2);
		} catch (Exception e) {
			if (e instanceof BadRequestException) {
				Assert.assertTrue(true);
			} else {
				fail();
			}

		}
	}
}
