package ApiTestCases;

import org.testng.annotations.Test;

import com.github.javafaker.Faker;

import utils.Log;

import apiConfigs.APIPath.apiPath;
import apiVerifications.APIVerification;
import apiConfigs.HeaderConfigs;
import baseTest.BaseTest;
import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import io.restassured.response.Response;
import pojo.Post_AddUserPojo;
import pojo.Get_UserID_From_ResponsePojo;

public class Post_AddUserTest extends BaseTest {
	static String User_ID;

	/**
	 * @return the User_ID
	 */
	public static String getUser_ID() {
		return User_ID;
	}

	/**
	 * @param userId the User_ID to set
	 */
	public static void setUser_ID(String user_ID) {
		User_ID = user_ID;
	}

	private Response response;
	private Get_UserID_From_ResponsePojo jsonresponse;

	@Test(priority=1)
	public void Post_UserTest() {
		Log.startTestCase("Add User");

		HeaderConfigs head = new HeaderConfigs();
		// generate random data (first name , last name , mail)
		Faker faker = new Faker();
		String FristName = faker.name().firstName();
		String LastName = faker.name().lastName();
		String emailrandom = faker.internet().emailAddress();

		Boolean active = true;
		Post_AddUserPojo pojo = new Post_AddUserPojo(FristName, LastName, emailrandom, active);
		RestAssured.urlEncodingEnabled = false;

		Log.info("body sent prameters ");
		System.out.println(pojo.toString());
		RestAssured.defaultParser = Parser.JSON;
		 response = (Response) RestAssured.given().when().headers(head.defaultHeaders()).body(pojo)
				.post(apiPath.Post_AddUser);
		Log.info("response  is printed ");

		jsonresponse = response.as(Get_UserID_From_ResponsePojo.class);

		System.out.println(response.getBody().asString());
	}
	@Test(priority=2)
		public void get_UserId_Test() {
		System.out.println("status code retrived is " + response.getStatusCode());
		Log.info("Get User Id value ");
		User_ID = jsonresponse.getId();
		// Log.info("Assert on the status code equal 20");
		// APIVerification.responseCodeValiddation(response, 20);
	}
	
	@Test(priority=3)
		public void Validate_Response_Code_201_Test() {
		Log.info("Assert on the status code equal 201");
		APIVerification.responseCodeValiddation(response, 201);
		}
		
	

	@Test(priority=4)
	public void Validate_Created_FristName_Test() {
		Log.info("validate  firstName value  is equal to the created name.");
		APIVerification.responseKeyValidationFromJsonObject(response, "firstName");
		
		

	}

}
