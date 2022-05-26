package RestAssured.RestAssuredProject;
/*
Rest Assured Exercise
   1. Open https://petstore.swagger.io/#/
   2. Use the USER API's   
   3. Perform the Get Operation  
   4. Perform the POST Operation with escape char , perform assertions for success code  
   5. Perform the POST Operation with JSON File , perform assertions for success code  
   6. Perform the PUT Operation with escape char, perform assertions for success code  
   7. Perform the PUT Operation with JSON File, perform assertions for success code  
   8. Perform the Delete Operation , perform assertions for success code  
   9. Create a scenario for 404 , and verify whether we are receiving 404 as status code or not (GET,POST,PUT,DELETE)   
   10. Perform the POST Operation with JSON File, perform assertions for success code, perform assertion to check whether any paramater code exist or not  & its value should be 200
   11. Try to create all these test cases as much Generic as possible.
 */

import org.apache.commons.io.IOUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;

public class PetSwagger 
{
	//4.Perform the POST Operation with escape char , perform assertions for success code 
	//Using JSON to String conversion
	
	@Test (description = "POST(Create User) using String as ReqBody", priority = 1, enabled = true)
	public void testcase1()
	{
		System.out.println("\nExecuting POST Method using String in Request body");
		String susername = "snd_UsingString";
		
		String ReqData = "{\"username\": \""+susername+"\",\"firstName\":\"sn\",\"lastName\":\"dn\",\"email\":\"sn@a.com\",\"password\":\"test12\",\"phone\":\"1234512345\",\"userStatus\":0}";
		System.out.println(ReqData);
		
		Response resp = GenericFunctions.CallFunction("/user", "POST", ReqData, ""); //call function
		Assert.assertEquals(resp.getStatusCode(), 200);
		
		System.out.println("\nPOST Method Status Code is: " +resp.getStatusCode());
		System.out.println("\nPOST Method Response is: " +resp.getBody().asPrettyString());

		//call GET method for newly created user
		System.out.println("\nVerifying User by GET details.......");
		Response resp1 = GenericFunctions.CallFunction("/user", "GET", susername, ""); //call function
		Assert.assertEquals(resp1.getStatusCode(), 200);
		
		System.out.println("\nGET Method Status Code is: " +resp1.getStatusCode());
		System.out.println("\nGET Method Response is: " +resp1.getBody().asPrettyString());
		String un = resp1.jsonPath().getString("username");
		System.out.println("\n Username in GET response is: "+un);
		
	}
	
	//5.Perform the POST Operation with JSON File , perform assertions for success code 
	/*10. Perform the POST with JSON File, perform assertions for success code, 
	perform assertion to check whether any paramater code exist or not  & its value should be 200 */
	
	@Test (description = "POST(Create User) using JSON file in ReqBody", priority = 2,enabled = true)
	public void testcase2() throws IOException, ParseException
	{
		System.out.println("\nExecuting POST Method using JSON file in Request body");
		
		FileInputStream fis = new FileInputStream (new File (System.getProperty("user.dir")+"\\data\\data.json"));
			
		//get username from file to compare later with the username from GET response
		JSONParser jsonParseObj = new JSONParser();
		FileReader reader = new FileReader((System.getProperty("user.dir")+"\\data\\data.json"));
			
		Object obj = jsonParseObj.parse(reader);
		JSONObject jsonobj = (JSONObject)obj;
		String fusername = (String)jsonobj.get("username");
		System.out.println("\n Username in JSON file is: "+fusername);
			
		String ReqData = IOUtils.toString(fis); //convert Json file to string
		System.out.println(ReqData);
			
		Response resp = GenericFunctions.CallFunction("/user", "POST", ReqData, ""); //call function
		Assert.assertEquals(resp.getStatusCode(), 200);
			
		System.out.println("\nPOST Method Status Code is: " +resp.getStatusCode());
		System.out.println("\nPOST Method Response is: " +resp.getBody().asPrettyString());
		
		//verify whether parameter code exist or not  & its value should be 200
		if (resp.jsonPath().get("code").equals(200))
		{
			System.out.println("The parameter 'Code' exists in the response & it is 200");
		}
		
		//call GET method for newly created user
		System.out.println("\nVerifying User by GET details.......");
		Response resp1 = GenericFunctions.CallFunction("/user", "GET", fusername, ""); //call function
		Assert.assertEquals(resp1.getStatusCode(), 200);
			
		System.out.println("\nGET Method Status Code is: " +resp1.getStatusCode());
		System.out.println("\nGET Method Response is: " +resp1.getBody().asPrettyString());
		String un = resp1.jsonPath().getString("username");
		System.out.println("\n Username in GET response is: "+un);
	}
	
	//3.Perform the Get Operation
	@Test (description = "GET(Get user by username)", priority = 5, enabled=true)
	public void testcase3()
	{
		System.out.println("\nExecuting GET Method");
		String username1 = "snd_UsingString123"; //define username to get details for
		String username2 = "sndUsingJSONFile123"; //define username to get details for
		
		Response resp = GenericFunctions.CallFunction("/user", "GET", username1, ""); //call function
		Assert.assertEquals(resp.getStatusCode(), 200);
		System.out.println("\nGET Method Status Code is: " +resp.getStatusCode());
		System.out.println("\nGET Method Response is: " +resp.getBody().asPrettyString());
		String un1 = resp.jsonPath().getString("username");
		System.out.println("\n Username in response is: "+un1);

		Response resp1 = GenericFunctions.CallFunction("/user", "GET", username2, ""); //call function
		Assert.assertEquals(resp1.getStatusCode(), 200);
		System.out.println("\nGET Method Status Code is: " +resp1.getStatusCode());
		System.out.println("\nGET Method Response is: " +resp1.getBody().asPrettyString());
		String un2 = resp1.jsonPath().getString("username");
		System.out.println("\n Username in response is: "+un2);
		
	}
	
	//6. Perform the PUT Operation with escape char, perform assertions for success code
	@Test (description = "PUT(Update User) using String as ReqBody", priority = 3, enabled = true)
	public void testcase4()
	{
		System.out.println("Executing PUT Method using String in Request body");
		String susername = "snd_UsingString";
		String pusername = "snd_UsingString123";
		
		String ReqData = "{\"username\": \""+pusername+"\",\"firstName\":\"sn\",\"lastName\":\"dn\",\"email\":\"sn@a.com\",\"password\":\"test12\",\"phone\":\"1234512345\",\"userStatus\":0}";
		System.out.println(ReqData);
		
		Response resp1 = GenericFunctions.CallFunction("/user/"+susername, "PUT", ReqData, ""); //call function
		Assert.assertEquals(resp1.getStatusCode(), 200);
		
		//call GET method for updated user
		System.out.println("\nVerifying User by GET details.......");
		Response res = GenericFunctions.CallFunction("/user", "GET", pusername, ""); //call function
		Assert.assertEquals(res.getStatusCode(), 200);
					
		System.out.println("\nGET Method Status Code is: " +res.getStatusCode());
		System.out.println("\nGET Method Response is: " +res.getBody().asPrettyString());
		String un = res.jsonPath().getString("username");
		System.out.println("\n Username in GET response is: "+un);
	}
	
	//7.Perform the PUT Operation with JSON File , perform assertions for success code 
	//Using JSON file
	@Test (description = "PUT(Update User) using JSON file in ReqBody", priority = 4,enabled = true)
	public void testcase5() throws IOException
	{
		System.out.println("Executing PUT Method using JSON file in Request body");
		String susername = "sndUsingJSONFile";
				
		FileInputStream fis = new FileInputStream (new File (System.getProperty("user.dir")+"\\data\\data1.json"));
		String ReqData = IOUtils.toString(fis);
		
		Response resp1 = GenericFunctions.CallFunction("/user/"+susername, "PUT", ReqData, ""); //call function
		Assert.assertEquals(resp1.getStatusCode(), 200);
		
		//call GET method for updated user
		System.out.println("\nVerifying User by GET details.......");
		String pusername = "sndUsingJSONFile123";
		Response res = GenericFunctions.CallFunction("/user", "GET", pusername, ""); //call function
		Assert.assertEquals(res.getStatusCode(), 200);
							
		System.out.println("\nGET Method Status Code is: " +res.getStatusCode());
		System.out.println("\nGET Method Response is: " +res.getBody().asPrettyString());
		String un = res.jsonPath().getString("username");
		System.out.println("\n Username in GET response is: "+un);		
	}
	
	//8. Perform the Delete Operation , perform assertions for success code
	@Test (description = "DELETE user", priority = 6, enabled = true)
	public void testcase6()
	{
		System.out.println("Executing DELETE Method");
		String username1 = "snd_UsingString123"; //define username to delete
		String username2 = "sndUsingJSONFile123"; //define username to delete
				
		Response resp1 = GenericFunctions.CallFunction("/user", "DELETE", username1, ""); //call function
		Assert.assertEquals(resp1.getStatusCode(), 200);
		System.out.println("\nDELETE Method Status Code is: " +resp1.getStatusCode());
		System.out.println("\nDELETE Method Response is: " +resp1.getBody().asPrettyString());
		assertEquals(resp1.jsonPath().getString("message"),"snd_UsingString123");
				
		Response resp2 = GenericFunctions.CallFunction("/user", "DELETE", username2, ""); //call function
		Assert.assertEquals(resp2.getStatusCode(), 200);
		System.out.println("\nDELETE Method Status Code is: " +resp2.getStatusCode());
		System.out.println("\nDELETE Method Response is: " +resp2.getBody().asPrettyString());
		assertEquals(resp2.jsonPath().getString("message"),"sndUsingJSONFile123");
	}

	//9. Create a scenario for 404 & verify whether we are receiving 404 as status code or not (GET,POST,PUT,DELETE)
	@Test (description = "TestCase - 404 validation",priority=7, enabled = true)
	public void testcase7()
	{
		String un = "InvalidUser";
		
		System.out.println("Verifying 404 Status code for GET Method..");
		Response res = GenericFunctions.CallFunction("/user", "GET", un, ""); //call function
		System.out.println("Status Code is: " +res.getStatusCode());
		System.out.println(res.body().asPrettyString());
		Assert.assertEquals(res.getStatusCode(),404);
		
		System.out.println("Verifying 404 Status code for DELETE Method..");
		Response res1 = GenericFunctions.CallFunction("/user", "DELETE", un, ""); //call function
		System.out.println("Status Code is: " +res1.getStatusCode());
		System.out.println(res1.body().asPrettyString());
		Assert.assertEquals(res1.getStatusCode(),404);
		
		
	}	
			
	/*10. Perform the POST with JSON File, perform assertions for success code, 
	perform assertion to check whether any paramater code exist or not  & its value should be 200 */
			
			@Test (description = "JSON Response validation", enabled = false)
			public void testcase8()
			{
				
			}
			
			
}



