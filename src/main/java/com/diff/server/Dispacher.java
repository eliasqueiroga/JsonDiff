package com.diff.server;

import java.io.DataInputStream;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.diff.server.annotations.Endpoint;
import com.diff.server.annotations.FormParam;
import com.diff.server.annotations.Path;
import com.diff.server.annotations.PathParam;
import com.diff.server.exception.EndpointInternalException;
import com.diff.server.exception.EndpointNotFoundException;
import com.diff.server.info.EndpointMetaInfo;

public class Dispacher {

	public Object findAndExecuteEndpoint(EndpointMetaInfo endpointMetaInfo) throws Exception {
		Class endpointClass = getEnpointClass(endpointMetaInfo.getEndpointName());

		if (endpointClass != null) {
			Object result = null;
			result = runEndpoint(endpointClass, endpointMetaInfo);

			if (result != null) {
				return EndpointResult.OK(result);
			} else {
				return EndpointResult.OK(null);
			}

		}else{
			throw new EndpointNotFoundException();
		}		
	}

	private Class getEnpointClass(String endpoint) throws Exception {
		Package packs = Package.getPackage("com.diff.endpoints");
		List<Class> allClasses = getClassesFromPackage(this.getClass().getClassLoader(), "com/diff/endpoints");

		for (Class clazz : allClasses) {
			if (clazz.isAnnotationPresent(Endpoint.class)) {
				Endpoint endpointAnnotation = (Endpoint) clazz.getAnnotation(Endpoint.class);
				if (endpointAnnotation.name().equalsIgnoreCase(endpoint)) {
					return clazz;
				}
			}
		}

		return null;
	}

	private List<Class> getClassesFromPackage(ClassLoader cl, String pack) throws Exception {
		String dottedPackage = pack.replaceAll("[/]", ".");
		List<Class> classes = new ArrayList<Class>();
		URL upackage = cl.getResource(pack);

		DataInputStream dis = new DataInputStream((InputStream) upackage.getContent());
		String line = null;
		while ((line = dis.readLine()) != null) {
			if (line.endsWith(".class")) {
				classes.add(Class.forName(dottedPackage + "." + line.substring(0, line.lastIndexOf('.'))));
			}
		}
		return classes;
	}

	private Object runEndpoint(Class endpointclass, EndpointMetaInfo endpointMetaInfo)
			throws EndpointNotFoundException, EndpointInternalException {
		Method[] allMethods = endpointclass.getMethods();

		for (Method method : allMethods) {
			if (method.isAnnotationPresent(Path.class)) {
				Path apiAnnotation = method.getAnnotation(Path.class);

				if (endpointMetaInfo.matchMethodEndpoint(apiAnnotation.name(), apiAnnotation.method().toString())) {
					Map<String, String> pathParamMapping = endpointMetaInfo.getPathParams(apiAnnotation.name());

					Map<String, String> formParamMapping = endpointMetaInfo.getFormParams();

					try {
						Object classInstance = endpointclass.newInstance();

						Object[] parameters = getEndpointParameters(method, pathParamMapping, formParamMapping);
						
						return method.invoke(classInstance, parameters);
					} catch (Exception e) {
						throw new EndpointInternalException(e.getCause().getMessage());
					}
				}
			}
		}

		throw new EndpointNotFoundException();
	}

	private Object[] getEndpointParameters(Method endpointMethod, Map<String, String> pathParamMapping,
			Map<String, String> formParamMapping) {

		Parameter[] parameters = endpointMethod.getParameters();
		Object[] paramValues = new Object[parameters.length];

		for (int j = 0; j < parameters.length; j++) {
			if (parameters[j].isAnnotationPresent(PathParam.class)) {
				String pathParam = parameters[j].getAnnotation(PathParam.class).value();

				paramValues[j] = (pathParamMapping.containsKey(pathParam) ? pathParamMapping.get(pathParam) : null);
			} else if (parameters[j].isAnnotationPresent(FormParam.class)) {
				String formParam = parameters[j].getAnnotation(FormParam.class).value();

				paramValues[j] = (formParamMapping.containsKey(formParam) ? formParamMapping.get(formParam) : null);

			} else {
				paramValues[j] = null;
			}
		}
		return paramValues;
	}
}
