# JSONDIFF

A simple web service to compare JSONS Base64 encoded

## Getting Started

It's a simple service that's able to receive two jsons in base64 encoded to be compared. Therefore three endpoints are provided to execute the comparison identified by a unique value passed in the URL.

 The endpoint to inform the left json to be compared

 * POST /v1/diff/<Comparison ID/left 
 * Parameter: 
     * json : A Json data in base64 format
 
 The endpoint to inform the right json to be compared
     
 * POST /v1/diff/<Comparison ID/right 
 * Parameter: 
     * json : A Json data in base64 format     

 The endpoint that performs the comparison between jsons left and right

 * GET /v1/diff/<Comparison ID>  
 
 All endpoint above returns a Json data with as result. The last one returns information about the comparison such as:
 
 * MISSING_BOTH_SIDES: If no jsons are informed before performing the comparison.
 * MISSING_RIGHT_SIDE: If only left json has been informed.
 * MISSING_LEFT_SIDE: If only right json has been informed.
 * SIZE_EQUAL: If both jsons have the same size. 
 * NOT_SIZE_EQUALS: If jsons do not have the same size.
 * SIZE_EQUAL_WITH_DIFFERENT_CONTENT: If both jsons have the same size, but the content is different. In this case, a list of offsets is also returned indicating where the differences are.
 
 
### Prerequisites

It requires [java](http://www.oracle.com/technetwork/pt/java/javase/downloads/index.html) Version 8+ and [Maven](https://maven.apache.org/download.cgi) to execute.  


## Running the tests

mvn clean test

### Break down into end to end tests

Unit tests - All unit tests are under the package test/com/diff
Integration tests - All integration tests are under the package test/integration


## Built With

 This code has been created under Lef - lightweight Endpoint Framework that was created together with this project. The reason is to provide an easy way to create endpoints and make them avaiable faster without complexity. So, instead of create servers, containers and many configuration, the idea of Lef os only focus on business rules. 
 
* [java](http://www.oracle.com/technetwork/pt/java/javase/downloads/index.html)
* [Maven](https://maven.apache.org/) - Dependency Management
* [Spark Java](http://sparkjava.com/) - The web framework used

## Deployment

As mentioned previously, this project works under the Lef framework. In order to create a new endpoint, it's only necessary to create a simple class,
annotate that classe with @Endpoint annotation informing the endpoint name and for each method of classe, annotate them with @Path annotation to specify the endpoint path pattern. Something like this.

 * 	@Path(name = "/:first_name/:last_name", method = Path.Method.POST)
 *  public void setName(@PathParam("first_name") String first_name, @PathParam("last_name") String last_name) {...}
 
So, when the url /Jose/Elias is called to the server, the method setName where the values "Jose" is associated to first_name parameter because "Jose" is the position defined by ":first_name" in the template mapping. The same happens with "Elias" and ":last_name"

To execute the code, two steps are neeeded:

 * Create a file config.properties in the root project folder indicating the package where the endpoint classes are.
 * Execute the main classe lef.server.Server


## Authors

* **José Elias Queiroga** - *Initial work* - [JSONDiff](https://github.com/eliasqueiroga)

