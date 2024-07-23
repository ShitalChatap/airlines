package com.maveric.project.FakeAPiTest;
 
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.equalToObject;
 
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import org.apache.http.HttpStatus;
import org.hamcrest.CoreMatchers;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
 
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.maveric.project.test.AirlinePojo;
import com.maveric.project.test.PassengerPojo;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
 
public class FakeApiTest 
{
	@BeforeClass
	public static void setUpEnv() 
	{
		RestAssured.baseURI="https://api.instantwebtools.net/v1";
	}
	@Test(testName ="Get all airlines details")
	public  void test1() throws DatabindException, IOException 
	{
		Response response=RestAssured.get("/airlines");
		Assert.assertEquals(response.getStatusCode(), 200);
		String responseBody=RestAssured.get("/airlines").then().assertThat().statusCode(200).and().log().body().toString();
		System.out.println(responseBody);
	}

	@Test(testName ="Get all passenger details ")
	public  void test2() throws DatabindException, IOException 
	{
		Response response=RestAssured.get("/passenger?page=0&size=10");
		Assert.assertEquals(response.getStatusCode(),200);
		String  responseBody=RestAssured.get("/passenger?page=0&size=10").then().assertThat().statusCode(200).and().log().body().toString();
		System.out.println(responseBody);
	}


	@Test(testName="Add new airline details using AirlineData.json file")
	public  void test3() throws IOException,DatabindException 
	{
		ObjectMapper mapper=new ObjectMapper();
		FileInputStream stream=new FileInputStream("./TestData/AirlineData.json");
		AirlinePojo  payload=mapper.readValue(stream, AirlinePojo.class);
			RestAssured.given()
			.contentType(ContentType.JSON)
			.body(payload)
			.post("/airlines")
			.then()
			.statusCode(HttpStatus.SC_OK)
			.and()
			.body("name", equalTo("Sri Lankan Airways"))
			.log()
			.all();
	}

 
	
 
	@Test(testName="Add new passenger details using PassengerData.json file")
	public  void test4() throws IOException,DatabindException
	{
		ObjectMapper mapper=new ObjectMapper();
		FileInputStream stream=new FileInputStream("./TestData/PassengerData.json");
		PassengerPojo  payload=mapper.readValue(stream, PassengerPojo.class);
			RestAssured.given()
			.contentType(ContentType.JSON)
			.body(payload)
			.post("/passenger")
			.then()
			.statusCode(HttpStatus.SC_OK)
			.and()
			.body("name", equalTo("John Doe"))
			.log()
			.all();
	}
	@Test(testName ="Get airlines details by airlineid")
	public  void test5() {
		RestAssured.get("/airlines/73dd5420-3bf9-48f3-a0b6-17cf7aa61b19")
		.then()
		.assertThat()
		.statusCode(200)
		.and()
		.body("name",equalToObject("American Airlines"));
	}

	@Test(testName ="Get passenger  by passengerid")
	public  void test6() {
		RestAssured.get("/passenger/66781445d6a086b675bb224e")
		.then()
		.assertThat()
		.statusCode(200)
		.and()
		.body("name",equalToObject("John Doe"));
	}

	@Test(testName = "update employee details")
    public void testUpdateEmployee7() throws IOException 
	{
		ObjectMapper mapper=new ObjectMapper();
		FileInputStream stream=new FileInputStream("./TestData/PassengerUpdateData.json");
		PassengerPojo  payload=mapper.readValue(stream, PassengerPojo.class);
 
		 System.out.println("Payload: " + mapper.writeValueAsString(payload));
		RestAssured.given()
		.contentType(ContentType.JSON)
		.body(payload)
		.put("/passenger/667945df7ad8fb0bc9433c48")
		.then()
		.statusCode(HttpStatus.SC_OK)
		.and()
		.body("name", equalTo("John Doe"))
		.log()
		.all();
    }
	@Test(testName="delete passenger details based on passengerid")
	public  void test8() {
        RestAssured.given()
                .delete("/passenger/667aafa27ad8fb55654342b0")
                .then()
                .assertThat()
                .statusCode(200)
                .body("message",equalToObject("Passenger data deleted successfully."));
		}
 
	@AfterClass
	public static void tearDownEnv() 
	{
		RestAssured.reset();
	}

}