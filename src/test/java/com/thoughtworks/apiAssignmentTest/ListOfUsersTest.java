package com.thoughtworks.apiAssignmentTest;

import static com.thoughtworks.apiAssignment.utilities.Constants.BASE_URL;
import static com.thoughtworks.apiAssignment.utilities.Constants.HTTP_OK_RESPONSE_CODE;
import static io.restassured.RestAssured.given;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.testng.AssertJUnit;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.thoughtworks.apiAssignment.utilities.PropertyUtils;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class ListOfUsersTest {

	private static final String USERSLIST_API_PATH = "api/users?page=2";
	private static Response response;
	private static String jsonAsString;
	private long expectedResponseTime = 5000L;

	@BeforeClass
	public static void setup() {
		RestAssured.baseURI = PropertyUtils.getPropertyValue(BASE_URL);
	}

	@Test
	public void testSuccessfulRetrievalOfListOfUsers() {

		response = given().when().get(USERSLIST_API_PATH);
		AssertJUnit.assertEquals(HTTP_OK_RESPONSE_CODE, response.getStatusCode());
		jsonAsString = response.asString();
		Map<String, String> jsonAsMap = JsonPath.from(jsonAsString).get("");
		AssertJUnit.assertEquals(jsonAsMap.size(), 6);
	}

	@Test
	public void testForResponseTime() {

		response = given().when().get(USERSLIST_API_PATH);
		long timeInS = response.timeIn(TimeUnit.MILLISECONDS);
		AssertJUnit.assertTrue(timeInS<expectedResponseTime);
	}
	
}
