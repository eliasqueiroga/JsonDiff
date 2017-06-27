package com.diff;

import com.diff.util.Util;

public class JsonUtil {

	/**
	 * This method is responsible to generate jsons to be used in all tests.
	 * Depend on values passed as parameter, it can return an array of strings
	 * with equal or different values, and if their values are encoded in base64
	 * format.
	 * 
	 * @param base64Format
	 *            If the jsons will be in base64 format.
	 * @param allEquals
	 *            If all jsons of array are equal or not.
	 * @param isValid
	 *            If all values are valid or not.
	 * @return
	 */
	public static String[] getJsons(boolean base64Format, boolean allEquals, boolean isValid) {
		String[] jsons = new String[2];

		String seedJson = null;

		if (isValid) {
			seedJson = "{ 'field1' : 'Field1', 'field2' : ['a', 'b'] }";
		} else {
			seedJson = "{ 'field1'  'Field1', 'field2' : 'a', ' }";
		}

		jsons[0] = seedJson;

		if (allEquals) {
			jsons[1] = seedJson;
		} else {
			jsons[1] = seedJson.replace("'Field1'", "true");
		}

		if (base64Format) {
			for (int i = 0; i < jsons.length; i++) {
				jsons[i] = Util.base64Encoder(jsons[i]);
			}
		}

		return jsons;
	}

}
