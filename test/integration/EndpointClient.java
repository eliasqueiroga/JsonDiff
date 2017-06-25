package integration;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;

import com.diff.endpoints.DiffEndpoint;
import com.diff.json.DiffResult;
import com.google.gson.Gson;

import lef.server.EndpointResult;
import lef.server.exception.BadRequestException;

/**
 * This class represents a client of Diff endpoint. It's responsible
 * to call all features provided by endpoints as a method calling, but makes a
 * HTTP call to server.   
 * 
 * @author Elias
 *
 */
public class EndpointClient extends DiffEndpoint {

	String host = "http://localhost:8080/v1";

	@Override
	public void setLeft(String id, String json) throws Exception {
		String url = host + "/diff/" + id + "/left";

		Map<String, String> data = new HashMap<String, String>();
		data.put("json", json);

		Map<String, String> response = post(url, data);

		if (!response.get("code").equalsIgnoreCase("200")) {
			throw new BadRequestException(response.get("content"));
		}
	}

	/* (non-Javadoc)
	 * @see com.diff.endpoints.DiffEndpoint#setright(java.lang.String, java.lang.String)
	 */
	@Override
	public void setright(String id, String json) throws Exception {
		String url = host + "/diff/" + id + "/right";

		Map<String, String> data = new HashMap<String, String>();
		data.put("json", json);

		Map<String, String> response = post(url, data);

		if (!response.get("code").equalsIgnoreCase("200")) {
			throw new BadRequestException(response.get("content"));
		}
	}

	/* (non-Javadoc)
	 * @see com.diff.endpoints.DiffEndpoint#getComparisonResult(java.lang.String)
	 */
	@Override
	public DiffResult getComparisonResult(String id) {
		String url = host + "/diff/" + id;
		
		Map<String, String> response = null;
		try {
			response = get(url);
		} catch (Exception e) {
			e.printStackTrace();
		}

		Gson gson = new Gson();
		EndpointResult endpointResult = gson.fromJson(response.get("content"), EndpointResult.class);
		
		DiffResult result = gson.fromJson(endpointResult.getContent().toString().replace("=", "='").replace(",", "',").replace("}", "'}"), DiffResult.class);
		return result;
	}

	/**
	 * Makes a POST request to a specific server.
	 * 
	 * @param url Place to send the request
	 * @param data The information passed as body data.
	 * @return A mapping with information about HTTP code and response content
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	private Map<String, String> post(String url, Map<String, String> data) throws ClientProtocolException, IOException {
		Map<String, String> responseData = new HashMap<String, String>();

		HttpClient client = HttpClientBuilder.create().build();
		HttpPost post = new HttpPost(url);

		List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
		for (String key : data.keySet()) {
			urlParameters.add(new BasicNameValuePair(key, data.get(key)));
		}

		post.setEntity(new UrlEncodedFormEntity(urlParameters));

		HttpResponse response = client.execute(post);

		responseData.put("code", String.valueOf(response.getStatusLine().getStatusCode()));

		BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

		StringBuffer result = new StringBuffer();
		String line = "";
		while ((line = rd.readLine()) != null) {
			result.append(line);
		}

		responseData.put("content", result.toString());

		return responseData;
	}

	/**
	 * Makes a GET HTTP request to a specific server.
	 * 
	 * @param url Place to send the request
	 * @return A mapping with information about HTTP code and response content
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	private Map<String, String> get(String url) throws ClientProtocolException, IOException {
		Map<String, String> responseData = new HashMap<String, String>();
		HttpClient client = HttpClientBuilder.create().build();
		HttpGet request = new HttpGet(url);

		HttpResponse response = client.execute(request);

		responseData.put("code", String.valueOf(response.getStatusLine().getStatusCode()));

		BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

		StringBuffer result = new StringBuffer();
		String line = "";
		while ((line = rd.readLine()) != null) {
			result.append(line);
		}
		responseData.put("content", result.toString());
		
		return responseData;
	}

}
