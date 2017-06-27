package com.diff.util;

import java.util.Random;

import org.junit.Assert;
import org.junit.Test;

public class UtilTest {

	private String generateRandomString(){
		 return Long.toString (Math.abs (new Random().nextLong ()), 36);
	}
	
	/**
	 * Converting a word in text plain format to base64 format.
	 */
	@Test
	public void convertToBase64() {
		String word = generateRandomString();
		
		String encodedWord = Util.base64Encoder(word);
		
		Assert.assertNotNull(encodedWord);
		
		Assert.assertTrue(!word.equals(encodedWord));
	}
	
	/**
	 * Converting a word in text plain format to base64 format.
	 */
	@Test
	public void convertABase64FormattToTextFormat() {
		String word = generateRandomString();
		
		String encodedWord = Util.base64Encoder(word);
		
		String decodedWord = Util.base64Decoder(encodedWord.getBytes());
		
		Assert.assertNotNull(decodedWord);
		
		Assert.assertEquals(word, decodedWord);
	}
	
	/**
	 * Converting a word in text plain format to base64 format.
	 */
	@Test
	public void convertoNonBase64ToText() {
		String word = generateRandomString();
		
		try{
			String decodedWord = Util.base64Decoder(word.getBytes());	
		}catch(Exception e){
			Assert.assertTrue(e instanceof IllegalArgumentException);	
		}
		
		
	}

}
