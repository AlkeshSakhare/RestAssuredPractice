package com.qa.api.automation;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;


public class RestBookerTest {
	  PrintStream log ;
	  String token="";
	RequestSpecification spec;
	@BeforeClass
	public void setUp() {
		try {
			log=new PrintStream(new FileOutputStream("./logs/logging.log"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		spec= RestAssured.given()
		.filter(RequestLoggingFilter.logRequestTo(log))
		.filter(ResponseLoggingFilter.logResponseTo(log))
				.baseUri("https://restful-booker.herokuapp.com");

		
	}
	
	@Test
	public void createToken()
	{
	token=	RestAssured.given()
			.accept(ContentType.JSON).contentType(ContentType.JSON)
		.spec(spec)
		.body("{\r\n" + 
				"    \"username\" : \"admin\",\r\n" + 
				"    \"password\" : \"password123\"\r\n" + 
				"}")
		.when()
		.post("auth")
		.then()
		.extract().jsonPath().getString("token");
		
	}
	
	@Test
	public void getAllBooking()
	{
		RestAssured.given()
		.spec(spec)
		.basePath("booking")
		.when()
		.get()
		.then()
		.statusCode(200);
	}
	@Test
	public void getBookingId()
	{
		RestAssured.given()
		.spec(spec)
		.basePath("booking")
		.when()
		.get("/12")
		.then()
		.statusCode(200);
	}

	@Test(enabled = false)
	public void deleteBookingId()
	{
		RestAssured.given()
				.accept(ContentType.JSON).contentType(ContentType.JSON)
		.spec(spec)
		.basePath("booking")
		.when()
		.delete("/12")
		.then()
		.statusCode(200);
	}
}
