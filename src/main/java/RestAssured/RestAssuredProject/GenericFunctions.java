package RestAssured.RestAssuredProject;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;


public class GenericFunctions 
{
	public static Response CallFunction(String URL,String Method, String data, String Parameter)
	{
		RestAssured.baseURI="https://petstore.swagger.io/v2";
		Response resp = null;
		if (Method.contentEquals("GET"))
		{
			//System.out.printf("\nEnd point: "+RestAssured.baseURI+URL);
			//System.out.printf("\nMethod: "+Method);
			//System.out.printf("\nData: "+data);
			//System.out.printf("\nParameter: "+Parameter);
			resp =	RestAssured.given().header("content-type","application/json").when().get(URL+"/"+data);
		}
		if(Method.contentEquals("POST"))
		{
			//System.out.printf("\nEnd point: "+RestAssured.baseURI+URL);
			//System.out.printf("\nMethod: "+Method);
			//System.out.printf("\nData: "+data);
			//System.out.printf("\nParameter: "+Parameter);
			resp =	RestAssured.given().header("content-type","application/json").body(data).when().post(URL);
		}
		if(Method.contentEquals("PUT"))
		{
			//System.out.printf("\nEnd point: "+RestAssured.baseURI+URL);
			//System.out.printf("\nMethod: "+Method);
			//System.out.printf("\nData: "+data);
			//System.out.printf("\nParameter: "+Parameter);
			resp =	RestAssured.given().header("content-type","application/json").body(data).when().put(URL);
		}
		if (Method.contentEquals("DELETE"))
		{
			//System.out.printf("\nEnd point: "+RestAssured.baseURI+URL);
			//System.out.printf("\nMethod: "+Method);
			//System.out.printf("\nData: "+data);
			//System.out.printf("\nParameter: "+Parameter);
			resp =	RestAssured.given().header("content-type","application/json").when().delete(URL+"/"+data);
		}
		
		return resp;
	}
	
}
