package com.thoughtworks.apiAssignmentTest;

import static com.thoughtworks.apiAssignment.utilities.Constants.APPLICATION_JSON;
import static com.thoughtworks.apiAssignment.utilities.Constants.BASE_URL;
import static com.thoughtworks.apiAssignment.utilities.Constants.HTTP_NO_CONTENT_RESPONSE_CODE;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.blankOrNullString;
import static org.hamcrest.Matchers.lessThan;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.thoughtworks.apiAssignment.utilities.PropertyUtils;

import io.restassured.RestAssured;

public class DeleteUserTest {

	private long expectedResponseTime = 5000L;
	
	private static final String DELETE_API_PATH = "/api/login";

	@BeforeClass
	public static void setup() {
		RestAssured.baseURI = PropertyUtils.getPropertyValue(BASE_URL);
	}

//Covering response time test in the same flow while deleting
	@Test
	public void testSuccessfulDeletedUser() {
		given().contentType(APPLICATION_JSON).when().delete(DELETE_API_PATH).then()
				.statusCode(HTTP_NO_CONTENT_RESPONSE_CODE).body(blankOrNullString()).time(lessThan(expectedResponseTime));
	}
	
	
}
