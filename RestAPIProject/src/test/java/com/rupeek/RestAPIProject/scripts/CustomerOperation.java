package com.rupeek.RestAPIProject.scripts;

import static io.restassured.RestAssured.given;

import org.json.simple.JSONObject;
import org.testng.annotations.Test;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
/**
 * 
 * @author Amit
 *
 */
public class CustomerOperation 
{

	String beartoken;
	String phoneno;
	/**
	 * to get beartoken
	 */
	@Test(priority=1)
	public void getBearToken()
	{
		JSONObject jsonObject=new JSONObject();
		jsonObject.put("username", "rupeek");
		jsonObject.put("password","password");
		Response response = given().contentType(ContentType.JSON).and().body(jsonObject.toJSONString()).when().
		post("http://13.126.80.194:8080/authenticate");
		beartoken=response.jsonPath().get("token");
		response.then().assertThat().contentType(ContentType.JSON);
		 
	}
	
	
	/**
	 * to get all customers details
	 */
	
	@Test(priority=2)
	public void getAllCustomerRecord()
	{
		Response response = given().auth().oauth2(beartoken)
	    .when().get("http://13.126.80.194:8080/api/v1/users");
		response.prettyPeek().then().assertThat().statusCode(200).and().contentType(ContentType.JSON);
	  phoneno= response.jsonPath().getString("phone[0]");
	}
	
	/**
	 * to get customer by phone no
	 */
	@Test(priority=3)
	public void getCustomerByPhoneNo()
	{
		given().auth().oauth2(beartoken).pathParam("phoneNo",phoneno)
	    .when().get("http://13.126.80.194:8080/api/v1/users/{phoneNo}").prettyPeek().then().
	    assertThat().statusCode(200).and().contentType(ContentType.JSON);
	}
	
	
	

}
