package lef.server;

import static spark.Spark.delete;
import static spark.Spark.exception;
import static spark.Spark.get;
import static spark.Spark.port;
import static spark.Spark.post;
import static spark.Spark.put;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Set;

import javax.servlet.MultipartConfigElement;

import com.diff.controllers.Controller;
import com.google.gson.Gson;

import lef.server.exception.BadRequestException;
import lef.server.exception.EndpointInternalException;
import lef.server.exception.EndpointMappingNotFoundException;
import lef.server.exception.EndpointNotFoundException;
import lef.server.info.Context;
import lef.server.info.EndpointMetaInfo;
import spark.Request;
import spark.ResponseTransformer;

/**
 * 
 * 
 * @author Elias
 *
 */
public class Server {

	private static String VERSION = "/v1";

	private static String PATH1 = VERSION + "/:endpoint/:path1";

	private static String PATH2 = VERSION + "/:endpoint/:path1/:path2";

	private static Controller controller = new Controller();

	/**
	 * 
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		Dispacher endpointDispacher = new Dispacher();

		port(8080);

		post(PATH1, (req, res) -> endpointDispacher.findAndExecuteEndpoint(getEndpointMetaInfo(req)),
				new JsonTransformer());

		get(PATH1, (req, res) -> endpointDispacher.findAndExecuteEndpoint(getEndpointMetaInfo(req)),
				new JsonTransformer());

		delete(PATH1, (req, res) -> endpointDispacher.findAndExecuteEndpoint(getEndpointMetaInfo(req)),
				new JsonTransformer());

		put(PATH1, (req, res) -> endpointDispacher.findAndExecuteEndpoint(getEndpointMetaInfo(req)),
				new JsonTransformer());

		post(PATH2, (req, res) -> endpointDispacher.findAndExecuteEndpoint(getEndpointMetaInfo(req)),
				new JsonTransformer());

		get(PATH2, (req, res) -> endpointDispacher.findAndExecuteEndpoint(getEndpointMetaInfo(req)),
				new JsonTransformer());

		delete(PATH2, (req, res) -> endpointDispacher.findAndExecuteEndpoint(getEndpointMetaInfo(req)),
				new JsonTransformer());

		put(PATH2, (req, res) -> endpointDispacher.findAndExecuteEndpoint(getEndpointMetaInfo(req)),
				new JsonTransformer());

		exception(EndpointNotFoundException.class, (e, req, res) -> {
			res.status(404);
			res.body(EndpointResult.ENDPOINT_NOT_FOUND().toString());
		});

		exception(EndpointInternalException.class, (e, req, res) -> {
			res.status(500);
			res.body(EndpointResult.ERROR(e.getMessage()).toString());
		});

		exception(EndpointMappingNotFoundException.class, (e, req, res) -> {
			res.status(404);
			res.body(EndpointResult.ENDPOINT_MAPPING_NOT_FOUND().toString());
		});

		exception(BadRequestException.class, (e, req, res) -> {
			res.status(400);
			res.body(EndpointResult.ERROR(e.getMessage()).toString());
		});

	}

	/**
	 * 
	 * @param request
	 * @return
	 */
	private static EndpointMetaInfo getEndpointMetaInfo(Request request) {
		String endpoint = request.params("endpoint");
		String path1 = request.params("path1");
		String path2 = request.params("path2");

		Set<String> paramSet = request.queryParams();
		EndpointMetaInfo endpointMetaInfo = new EndpointMetaInfo(endpoint,
				new Context(request.host(), request.protocol(), request.port(), request.requestMethod()), path1, path2);

		for (String param : paramSet) {
			endpointMetaInfo.getFormParams().put(param, request.queryParams(param));
		}

		return endpointMetaInfo;
	}

	static class JsonTransformer implements ResponseTransformer {

		private Gson gson = new Gson();

		@Override
		public String render(Object model) {
			return gson.toJson(model);
		}

	}

}
