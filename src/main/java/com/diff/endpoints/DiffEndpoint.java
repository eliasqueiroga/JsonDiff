package com.diff.endpoints;

import com.diff.controllers.Controller;
import com.diff.json.DiffResult;
import com.diff.server.annotations.Endpoint;
import com.diff.server.annotations.FormParam;
import com.diff.server.annotations.Path;
import com.diff.server.annotations.PathParam;

/**
 * This endpoint class contains methods to receive two jsons and compare them.
 *  All methods are mapped to a different endpoint and thet are executed when
 *  the endpoint is called. For example, 
 *  
 *   - A post request to /v1/diff/12/left will execute the setLeft() method,
 *     and all arguments passed to url are passed to method as well. 
 *     
 *  The setLeft method is annotated with @PAth that defines the 
 *  endpoint path that it is connected to. So, /:ID/left is the url pattern where
 *  :ID is the variable name defined in annotation parameter @PathParam. 
 * 
 * @author Elias
 *
 */
@Endpoint(name="diff")
public class DiffEndpoint {
	
	Controller diffController = new Controller();

	/**
	 * Sets the left json.
	 * 
	 * @param id The comparison identification.
	 * @param json The json data to be compared. It is in base64 format
	 * @return A default message saying the json was set to be compared.
	 */
	@Path(name="/:id/left", method=Path.Method.POST)
	public String setLeft(@PathParam("id") String id, @FormParam("json") String json){
		diffController.setLeftSide(id, json);
		
		return "OK";
	}
	
	/**
	 * Sets the right json.
	 * 
	 * @param id The comparison identification.
	 * @param json The json data to be compared. It is in base64 format
	 * @return A default message saying the json was set to be compared.
	 */	
	@Path(name="/:id/:right", method=Path.Method.POST)
	public String setright(@PathParam("id") String id, @FormParam("json") String json){
		diffController.setRightSide(id, json);
		
		return "OK";
	}

	/**
	 * This endpoint is responsible to compare the left and right jsons (informed in endpoints above),
	 * and provides a result of this comparison.
	 * 
	 * @param id The comparison id. 
	 * @return An instance of DiffResult that contains information about the comparison.
	 */
	@Path(name="/:id", method=Path.Method.GET)
	public DiffResult getComparisonResult(@PathParam("id") String id){
		DiffResult result = diffController.compare(id);
		
		return result;
	}

	
}
