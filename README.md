# JSONDIFF

A simple web service to compare JSONS Base64 encoded

## Getting Started
It's a simple service that's able to receive two jsons in base64 encoded to be compared. Therefore three endpoints are provided to execute the comparison identified by a unique value passed in the URL.

###### Defining the left JSON
Informing the left JSON data to be compared. Notice an ID is defined, this value will be used by comparator endpoint in order to find the correct data (left/right) in the comparison action.
	
 	POST /v1/diff/<Diff ID>/left 
 	Parameter: 
    - json : A Json data in base64 format
	
###### Defining the right JSON	
 Similar to left JSON, the following endpoint is used to define the right JSON data. The comparison ID also needs to be informed.
     
    POST /v1/diff/<Diff ID>/right 
    Parameter: 
    - json : A Json data in base64 format     

###### Comparing both informed JSONs
 The endpoint that performs the comparison between jsons left and right
 
    GET /v1/diff/<Diff ID>  
 
 All endpoints above returns a JSON data as result. The last one returns information about the comparison such as:
 
```json
{ 
    status : "Success",
    content : {
                status : "SIZE_EQUAL"
               }
}
```
 Where the information in status field can have the following values to state the comparison result:
 - MISSING_BOTH_SIDES: If no jsons are informed before performing the comparison.
 - MISSING_RIGHT_SIDE: If only left json has been informed.
 - MISSING_LEFT_SIDE: If only right json has been informed.
 - SIZE_EQUAL: If both jsons have the same size. 
 - NOT_SIZE_EQUALS: If jsons do not have the same size.
 - SIZE_EQUAL_WITH_DIFFERENT_CONTENT: If both jsons have the same size, but the content are different. In this case, a list of offsets is also returned indicating where the differences are.

If the JSON contents are different even their sizes are equals, the result shows a list of all offsets where the differences are:

```json
{   
    status : "Success",
    content : {
                status :"SIZE_EQUAL_WITH_DIFFERENT_CONTENT",
                offsets : [13]
              }
}
```

###### Examples
Setting the left JSON
```bash
curl http://localhost:8080/v1/diff/ID1/left -d "json=eyAnYScgOiAnQScgfQ=="
{"status":"Success"}
```
Setting the right JSON
```bash
curl http://localhost:8080/v1/diff/ID1/right -d "json=eyAnYScgOiAnQicgfQ=="
{"status":"Success"}
```
Comparing left and right sides
```bash
curl http://localhost:8080/v1/diff/ID1
{"status":"Success","content":{"status":"SIZE_EQUAL"}}
```
### Prerequisites

It requires [java](http://www.oracle.com/technetwork/pt/java/javase/downloads/index.html) Version 8+ and [Maven](https://maven.apache.org/download.cgi) to execute.  


## Running the tests

The project contains unit tests and integration tests. The unit tests cover all classes of JSONDiff project testing all internal features and their methods. The integration tests are able to run tests end-to-end sending HTTP requests to JSONDiff endpoints and verifying all responses.

So, a tip is to start the Lef server before running all tests because the integration test requires the server running on port 8080.

To start the server on port 8080

```bash
mvn exec:java
```
To start all tests

```bash
mvn clean test
```

## Built With

 This code has been created under Lef - The Lightweight Endpoint Framework that was created together with this project. The reason is to provide an easy way to create REST endpoints and make them available faster without complexity. So, the main idea of Lef is to focus on business rules. 
 More information about the Lef is provided in the development section.
 
* [java](http://www.oracle.com/technetwork/pt/java/javase/downloads/index.html)
* [Maven](https://maven.apache.org/) - Dependency Management
* [Spark Java](http://sparkjava.com/) - The web framework used as base web framework 

## Deployment

As mentioned previously, this project works under the Lef framework. In order to create a new endpoint, it's only necessary to create a simple class, annotating with @Endpoint annotation informing the endpoint name and for each method of class, annotate them with @Path annotation to specify the endpoint path pattern. 

```java
@Endpoint(name="test")
public class Test{
 	@Path(name = "/:first_name/:last_name", method = Path.Method.POST)
 	public void setName(@PathParam("first_name") String first_name, @PathParam("last_name") String last_name) { }
}
```

So, the command below can be used to start the Lef framework that is started on port 8080.

```bash
mvn exec:java
```

Now, it's possible to make HTTP requests to the server following the pattern /V1/<endpoint>/<path1>/<path2>. When a request is sent to the server  following that url pattern, the Lef will execute the class.method that matches with values used in the url.

For example: The url /v1/test/Jose/Elias is called to the server. It means the class Test specified above will be selected because it's annotated with @Endpoint and the annotation name matches with the value "test" present in the url. After that, the paths will be checked matching with values specified in @Path annotation of methods in that class. In this case, the method setName is chosen. The values present in the url may be passed as argumento to method if the argument is annotated with @PathParam.

The current version of Lef supports only path parameters for REST pattern and form parameters for POST requests.  Query string parameters are not being supported in current version.
 
To execute the code, the following steps are neeeded:

 - Define endpoints package: 
   The Lef looks for endpoint classes in a specific package of the project. So, the config.properties file needs to be created in the root project folder  and the parameter endpoint_package indicates the package where the endpoint classes are. Exemple: 
```
endpoint_package=com.diff.endpoints
````

 - Execute the Lef server:
   Using the following maven command, the server is started on port 8080.
   
``` bash
mvn exec:java
```

Once the enpoint's package was defined in config file and the server is up! The project is ready to be used.

###### Future work
The current version of Lef has some limitations and they can be implemented in future work. Some of them:
 - QueryString mapping
 - Server port in config file
 - A endpoint mapping could be created on server startup to be consumed for each request calling instead of interate for all classes looking for annotated classes. This feature will improve the performance a lot.
 - Allow to inject general data about the request in the endpoint class such as host, protocol, port, headers etc.
 - And others that I don't remember now. :)
## Authors

* **José Elias Queiroga** - *Initial work* - [JSONDiff](https://github.com/eliasqueiroga)

