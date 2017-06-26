package com.diff.json;

import java.util.ArrayList;
import java.util.List;

import com.diff.util.Util;

public class Testejson {
	
	public static void main(String[] args) {
//		JsonReader jsonReader = Json.createReader(new StringReader("{ 'a' : 'B' }"));
//		JsonObject object = jsonReader.readObject();
//		
//		System.out.println(object.toString());
//		jsonReader.close();	
		
		String a = Util.base64Encoder("{ 'a' : true }");
		String b = Util.base64Encoder("{ 'b' : true }");
		
		System.out.println(a);
		System.out.println(b);
		
		long diff = Base64BitsDifferent(a, b);
		
		System.out.println(diff);
	}

	private static long Base64BitsDifferent(String first64, String second64)
	{
	    long toReturn = 0;

	    byte[] firstBytes = first64.getBytes();
	    byte[] secondBytes = second64.getBytes();
	    byte different = 0;
	    List<String> offsets = new ArrayList<String>();
	    
	    if (firstBytes.length == secondBytes.length){
		    for (int i = 0; i < firstBytes.length; i++) {
		    	if (!(new Byte(firstBytes[i]).equals(new Byte(secondBytes[i])))){
		    		offsets.add(String.valueOf(i));
		    	}		    		
		    }	    	
	    }

	    return toReturn;
	}
	
}
