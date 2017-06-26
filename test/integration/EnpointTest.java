package integration;

import java.util.Random;

import org.junit.Assert;
import org.junit.Test;

import com.diff.JsonUtil;
import com.diff.json.DiffResult;

import lef.server.exception.BadRequestException;

public class EnpointTest {

	/**
	 * Make a HTTP request informing left and right jsons to be compared.
	 * The jsons are equals.
	 */
	@Test
	public void compareEqualsJsonTest() {
		EndpointClient client = new EndpointClient();
		String id = "ID_" + new Random().nextLong();
		String[] jsons = JsonUtil.getJsons(true, true, true);
		try {
			client.setLeft(id, jsons[0]);
			client.setright(id, jsons[1]);
		
			DiffResult result = client.getComparisonResult(id);
			
			Assert.assertNotNull(result);
			
			Assert.assertEquals(result.getStatus(), DiffResult.Status.SIZE_EQUAL);
		} catch (Exception e) {
			Assert.fail();
		}
	}

	/**
	 * Make a HTTP request informing left and right jsons to be compared.
	 * The jsons are not equals.
	 */
	@Test
	public void compareDifferentJsonTest() {
		EndpointClient client = new EndpointClient();
		String id = "ID_" + new Random().nextLong();
		String[] jsons = JsonUtil.getJsons(true, false, true);
		try {
			client.setLeft(id, jsons[0]);
			client.setright(id, jsons[1]);
		
			DiffResult result = client.getComparisonResult(id);
			
			Assert.assertNotNull(result);
			
			Assert.assertEquals(result.getStatus(), DiffResult.Status.NOT_SIZE_EQUALS);
		} catch (Exception e) {
			Assert.fail();
		}
	}

	/**
	 * Make a HTTP request informing left and right jsons to be compared.
	 * The jsons are not equals.
	 */
	@Test
	public void tryWithOnlyOneSideTest() {
		EndpointClient client = new EndpointClient();
		String id = "ID_" + new Random().nextLong();
		String[] jsons = JsonUtil.getJsons(true, false, true);
		try {
			client.setLeft(id, jsons[0]);
		
			DiffResult result = client.getComparisonResult(id);
			
			Assert.assertNotNull(result);
			
			Assert.assertEquals(result.getStatus(), DiffResult.Status.MISSING_RIGHT_SIDE);
		} catch (Exception e) {
			Assert.fail();
		}
	}
	
	/**
	 * Make a HTTP request with json to be compated.
	 */
	@Test
	public void tryWithoutBothSidesTest() {
		EndpointClient client = new EndpointClient();
		String id = "ID_" + new Random().nextLong();
		try {
		
			DiffResult result = client.getComparisonResult(id);
			
			Assert.assertNotNull(result);
			
			Assert.assertEquals(result.getStatus(), DiffResult.Status.MISSING_BOTH_SIDES);
		} catch (Exception e) {
			Assert.fail();
		}
	}

	/**
	 * Try to compate nothing, after that inform left side 
	 * and try to compare only with it, and then inform the right 
	 * side in order to compare both.
	 */
	@Test
	public void compareCompleteSidesTest() {
		EndpointClient client = new EndpointClient();
		String id = "ID_" + new Random().nextLong();
		String[] jsons = JsonUtil.getJsons(true, true, true);
		try {
		
			DiffResult result = client.getComparisonResult(id);
			
			Assert.assertNotNull(result);
			
			Assert.assertEquals(result.getStatus(), DiffResult.Status.MISSING_BOTH_SIDES);
			
			client.setLeft(id, jsons[0]);
			
			result = client.getComparisonResult(id);
			
			Assert.assertEquals(result.getStatus(), DiffResult.Status.MISSING_RIGHT_SIDE);
			
			client.setright(id, jsons[1]);
			
			result = client.getComparisonResult(id);
			
			Assert.assertEquals(result.getStatus(), DiffResult.Status.SIZE_EQUAL);
			
		} catch (Exception e) {
			Assert.fail();
		}		
	}
	
	/**
	 * Try to make request to compare null jsons
	 */
	@Test
	public void tryToCompareNullJsonsTest() {
		EndpointClient client = new EndpointClient();
		String id = "ID_" + new Random().nextLong();
		try {
			client.setLeft(id, null);
			client.setright(id, null);
			
			DiffResult result = client.getComparisonResult(id);
			
			Assert.assertNotNull(result);
		} catch (Exception e) {
			if (e instanceof BadRequestException){
				Assert.assertTrue(true);
			}else{
				Assert.fail();
			}
		}
	}
	
	/**
	 * Try to make request to compare jsons already
	 * in json format.
	 */
	@Test
	public void tryToCompareStringTest() {
		EndpointClient client = new EndpointClient();
		String id = "ID_" + new Random().nextLong();
		String[] jsons = JsonUtil.getJsons(false, true, true);
		try {
			client.setLeft(id, jsons[0]);
			client.setright(id, jsons[1]);
			
			DiffResult result = client.getComparisonResult(id);
			
			Assert.assertNotNull(result);
		} catch (Exception e) {
			if (e instanceof BadRequestException){
				Assert.assertTrue(true);
			}else{
				Assert.fail();
			}
		}
	}
	
	/**
	 * Try to make request to compare invalid jsons.
	 */
	@Test
	public void tryToInvalidJsonTest() {
		EndpointClient client = new EndpointClient();
		String id = "ID_" + new Random().nextLong();
		String[] jsons = JsonUtil.getJsons(true, true, false);
		try {
			client.setLeft(id, jsons[0]);
			client.setright(id, jsons[1]);
			
			DiffResult result = client.getComparisonResult(id);
			
			Assert.assertNotNull(result);
		} catch (Exception e) {
			if (e instanceof BadRequestException){
				Assert.assertTrue(true);
			}else{
				Assert.fail();
			}
		}
	}
	

	
}
