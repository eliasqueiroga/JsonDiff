package com.diff.server.util;

import java.util.HashMap;
import java.util.Map;

public class Util {

	public static String[] splitEndpointPath(String endpointPath){
		return endpointPath.replace("/", " ").trim().replaceAll("\\s+", ".").split("\\.");
	}
	
	public static Map<String, String> isMethodMapped(String pathAnnotated, String... paths) {
		String[] splitAnnotation = pathAnnotated.replace("/", " ").trim().replaceAll("\\s+", ".").split("\\.");
		Map<String, String> endpointPathsMapping = new HashMap<String, String>();
		
		if (paths != null && paths[1] == null) {
			if (splitAnnotation.length == 1) {
				if (splitAnnotation[0].startsWith(":")) {
					endpointPathsMapping.put(splitAnnotation[0].replace(":", ""), paths[0]);
				}else if (!splitAnnotation[0].equalsIgnoreCase(paths[0])) {
					return null;
				}
			} else {
				return null;
			}
		} else if (splitAnnotation.length == 2) {
			if (splitAnnotation[0].startsWith(":")) {
				endpointPathsMapping.put(splitAnnotation[0].replace(":", ""), paths[0]);
			}else if (!splitAnnotation[0].equalsIgnoreCase(paths[0])) {
				return null;
			}

			if (splitAnnotation[1].startsWith(":")) {
				endpointPathsMapping.put(splitAnnotation[1].replace(":", ""), paths[1]);
			}else if (!splitAnnotation[1].equalsIgnoreCase(paths[1])) {
				return null;
			}
		} else {
			return null;
		}
		
		return endpointPathsMapping;
	}
	
}
