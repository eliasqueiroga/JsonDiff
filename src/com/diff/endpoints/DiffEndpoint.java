package com.diff.endpoints;

import java.nio.charset.StandardCharsets;

import com.diff.controllers.Controller;
import com.diff.json.JSONDiffResult;

import lef.server.annotations.Endpoint;
import lef.server.annotations.FormParam;
import lef.server.annotations.Path;
import lef.server.annotations.PathParam;
import lef.server.exception.BadRequestException;

/**
 * This endpoint class contains methods to receive two jsons and compare them.
 * All methods are mapped to a different endpoint and they are executed when the
 * endpoint is called. For example, a post request to /v1/diff/12/left will execute 
 * the setLeft() method, and all parameters passed to url are passed to method as well.
 * 
 * The setLeft method is annotated with @PAth that defines the endpoint path, this
 * way it's possible to create a mapping between a method to a endpoint URL.
 * So, /:ID/left is the url pattern where :ID is the
 * variable defined in annotation parameter @PathParam.
 * 
 * To clarify, given the following method.
 * 
 * 	@Path(name = "/:first_name/:last_name", method = Path.Method.POST)
 *  public void setName(@PathParam("first_name") String first_name, @PathParam("last_name") String last_name) {...}
 *
 * Notice the method above is annotated with @Path and its attribute name contains the 
 * value "/:first_name/:last_name".The annotated method setName has two parameters where both are 
 * annotated with @PathParam where the values are the same used in @Path annotation.
 * 
 * So, when the url /Jose/Elias is called to the server, the method setName where the values 
 * "Jose" is assotiated to first_name parameter because "jose" is the position defined by ":first_name"
 * in the template mapping. The same happens with "Elias" and ":last_name"
 * 
 * 
 * @author Elias
 *
 */
@Endpoint(name = "diff")
public class DiffEndpoint {

	Controller diffController = new Controller();

	/**
	 * Sets the left json data to be compared.
	 * 
	 * @param id
	 *            The comparison identification.
	 * @param json
	 *            The json data to be compared. It is in base64 format
	 * @return A default message saying the json was set to be compared.
	 */
	@Path(name = "/:id/left", method = Path.Method.POST)
	public void setLeft(@PathParam("id") String id, @FormParam("json") String json) throws Exception {
		if (id != null) {
			diffController.setLeftSide(id, json.getBytes(StandardCharsets.UTF_8));
		} else {
			throw new BadRequestException("The comparison ID must be informed");
		}
	}

	/**
	 * Sets the right json.
	 * 
	 * @param id
	 *            The comparison identification.
	 * @param json
	 *            The json data to be compared. It is in base64 format
	 * @return A default message saying the json was set to be compared.
	 */
	@Path(name = "/:id/right", method = Path.Method.POST)
	public void setright(@PathParam("id") String id, @FormParam("json") String json) throws Exception {
		if (id != null) {
			diffController.setRightSide(id, json.getBytes(StandardCharsets.UTF_8));
		} else {
			throw new BadRequestException("The comparison ID must be informed");
		}
	}

	/**
	 * This endpoint is responsible to compare the left and right jsons
	 * (informed in endpoints above), and provides a result of this comparison.
	 * 
	 * @param id
	 *            The comparison id.
	 * @return An instance of DiffResult that contains information about the
	 *         comparison.
	 */
	@Path(name = "/:id", method = Path.Method.GET)
	public JSONDiffResult getComparisonResult(@PathParam("id") String id) {
		JSONDiffResult result = diffController.compare(id);

		return result;
	}

}
