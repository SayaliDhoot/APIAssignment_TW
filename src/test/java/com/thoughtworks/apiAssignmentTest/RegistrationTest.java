package com.thoughtworks.apiAssignmentTest;

import static com.thoughtworks.apiAssignment.utilities.Constants.APPLICATION_JSON;
import static com.thoughtworks.apiAssignment.utilities.Constants.BASE_URL;
import static com.thoughtworks.apiAssignment.utilities.Constants.HTTP_BAD_REQUEST_RESPONSE_CODE;
import static com.thoughtworks.apiAssignment.utilities.Constants.HTTP_OK_RESPONSE_CODE;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.lessThan;
import static org.hamcrest.Matchers.notNullValue;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.thoughtworks.apiAssignment.RegistrationRequest;
import com.thoughtworks.apiAssignment.utilities.PropertyUtils;

import io.restassured.RestAssured;

public class RegistrationTest {
	private static final String REGISTRATION_API_PATH = "/api/register";
	private static final String VALID_EMAIL = "eve.holt@reqres.in";
	private static final String VALID_PASSWORD = "pistol";
	private static final String INVALID_EMAIL = "TW";
	private long expectedResponseTime = 5000L;

	@BeforeClass
	public static void setup() {
		RestAssured.baseURI = PropertyUtils.getPropertyValue(BASE_URL);
	}

	@Test
	public void testSuccessfulRegistration() {
		RegistrationRequest registrationRequest = RegistrationRequest.builder().email(VALID_EMAIL)
				.password(VALID_PASSWORD).build();
		given().contentType(APPLICATION_JSON).body(registrationRequest).when().post(REGISTRATION_API_PATH).then()
				.statusCode(HTTP_OK_RESPONSE_CODE).body("id", notNullValue()).body("token", notNullValue());
	}

	@Test
	public void testFailedRegistrationWithNoEmailAndUsername() {
		RegistrationRequest registrationRequest = RegistrationRequest.builder().build();
		given().contentType(APPLICATION_JSON).body(registrationRequest).when().post(REGISTRATION_API_PATH).then()
				.statusCode(HTTP_BAD_REQUEST_RESPONSE_CODE).body("error", equalTo("Missing email or username"));
	}

	@Test
	public void testFailedRegistrationWithNoPassword() {
		RegistrationRequest registrationRequest = RegistrationRequest.builder().email(VALID_EMAIL).build();
		given().contentType(APPLICATION_JSON).body(registrationRequest).when().post(REGISTRATION_API_PATH).then()
				.statusCode(HTTP_BAD_REQUEST_RESPONSE_CODE).body("error", equalTo("Missing password"));
	}

	@Test
	public void testFailedRegistrationWithNoEmail() {
		RegistrationRequest registrationRequest = RegistrationRequest.builder().password(VALID_PASSWORD).build();
		given().contentType(APPLICATION_JSON).body(registrationRequest).when().post(REGISTRATION_API_PATH).then()
				.statusCode(HTTP_BAD_REQUEST_RESPONSE_CODE).body("error", equalTo("Missing email or username"));
	}

	@Test
	public void testFailedRegistrationWithInvalidEmail() {
		RegistrationRequest registrationRequest = RegistrationRequest.builder().email(INVALID_EMAIL)
				.password(VALID_PASSWORD).build();
		given().contentType(APPLICATION_JSON).body(registrationRequest).when().post(REGISTRATION_API_PATH).then()
				.statusCode(HTTP_BAD_REQUEST_RESPONSE_CODE)
				.body("error", equalTo("Note: Only defined users succeed registration"));
	}

	@Test
	public void testForResponseTimeForDeleteUser() {
		RegistrationRequest registrationRequest = RegistrationRequest.builder().email(VALID_EMAIL)
				.password(VALID_PASSWORD).build();
		given() .contentType(APPLICATION_JSON)
				.body(registrationRequest)
				.when().post(REGISTRATION_API_PATH)
				.then()
				.statusCode(HTTP_OK_RESPONSE_CODE)
				.time(lessThan(expectedResponseTime));
	}
	
}
