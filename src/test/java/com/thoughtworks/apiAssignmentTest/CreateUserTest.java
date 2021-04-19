package com.thoughtworks.apiAssignmentTest;

import static com.thoughtworks.apiAssignment.utilities.Constants.APPLICATION_JSON;
import static com.thoughtworks.apiAssignment.utilities.Constants.BASE_URL;
import static com.thoughtworks.apiAssignment.utilities.Constants.HTTP_CREATED_RESPONSE_CODE;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.lessThan;
import static org.hamcrest.Matchers.notNullValue;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.thoughtworks.apiAssignment.CreateUserRequest;
import com.thoughtworks.apiAssignment.utilities.PropertyUtils;

import io.restassured.RestAssured;

public class CreateUserTest {

	private static final String CREATEUSER_API_PATH = "api/users";
	private static final String NAME = "morpheus";
	private static final String JOB = "leader";
	private static final String BlankNAME = "";
	private static final String BlankJOB = "";
	private long expectedResponseTime = 5000L;

	@BeforeClass
	public static void setup() {
		RestAssured.baseURI = PropertyUtils.getPropertyValue(BASE_URL);
	}

	@Test
	public void testSuccessfulCreateUserWithBothNameAndJob() {
		CreateUserRequest createUserRequest = CreateUserRequest.builder()
				.name(NAME).job(JOB).build();
		given().contentType(APPLICATION_JSON)
				.body(createUserRequest)
				.when()
				.post(CREATEUSER_API_PATH)
				.then()
				.statusCode(HTTP_CREATED_RESPONSE_CODE)
				.contentType(APPLICATION_JSON)
				.body("name", equalTo(NAME)).body("job", equalTo(JOB))
				.body("id", notNullValue()).body("createdAt", notNullValue());
	}

	@Test
	public void testSuccessfulCreateUserWithOnlyName() {
		CreateUserRequest createUserRequest = CreateUserRequest.builder().name(NAME).build();
		given().contentType(APPLICATION_JSON).body(createUserRequest).when().post(CREATEUSER_API_PATH).then()
				.statusCode(HTTP_CREATED_RESPONSE_CODE)
				.contentType(APPLICATION_JSON)
				.body("name", equalTo(NAME))
				.body("id", notNullValue())
				.body("createdAt", notNullValue());
	}

	@Test
	public void testSuccessfulCreateUserWithOnlyJob() {
		CreateUserRequest createUserRequest = CreateUserRequest.builder().job(JOB).build();
		given().contentType(APPLICATION_JSON).body(createUserRequest).when().post(CREATEUSER_API_PATH).then()
				.statusCode(HTTP_CREATED_RESPONSE_CODE)
				.contentType(APPLICATION_JSON)
				.body("job", equalTo(JOB)).body("id", notNullValue())
				.body("createdAt", notNullValue());
	}

	@Test
	public void testSuccessfulCreateUserWithNoNameNoJob() {
		// As per site, it does pass, user gets created, even when no name and job
		// provided
		CreateUserRequest createUserRequest = CreateUserRequest.builder().build();
		given().contentType(APPLICATION_JSON).body(createUserRequest).when().post(CREATEUSER_API_PATH).then()
				.statusCode(HTTP_CREATED_RESPONSE_CODE).body("id", notNullValue()).body("createdAt", notNullValue());
	}

	@Test
	public void testSuccessfulCreateUserWithBlankNameAndJob() {
		CreateUserRequest createUserRequest = CreateUserRequest.builder().name(BlankNAME).job(BlankJOB).build();
		given().contentType(APPLICATION_JSON).body(createUserRequest).when().post(CREATEUSER_API_PATH).then()
				.statusCode(HTTP_CREATED_RESPONSE_CODE)
				.contentType(APPLICATION_JSON)
				.body("name", equalTo(BlankNAME)).body("job", equalTo(BlankJOB))
				.body("id", notNullValue()).body("createdAt", notNullValue());
	}

	
	@Test
	public void testForResponseTimeForSuccessfulCreateUser() {
		CreateUserRequest createUserRequest = CreateUserRequest.builder()
				.name(NAME).job(JOB).build();
		given().contentType(APPLICATION_JSON)
				.body(createUserRequest)
				.when()
				.post(CREATEUSER_API_PATH)
				.then()
				.statusCode(HTTP_CREATED_RESPONSE_CODE)
				.time(lessThan(expectedResponseTime));
	}
	
}
