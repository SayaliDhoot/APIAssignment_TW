package com.thoughtworks.apiAssignmentTest;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import static com.thoughtworks.apiAssignment.utilities.Constants.*; 
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.lessThan;
import com.thoughtworks.apiAssignment.LoginRequest;
import com.thoughtworks.apiAssignment.utilities.PropertyUtils;
import io.restassured.RestAssured;

public class LoginTest {

	private static final String LOGIN_API_PATH = "/api/login"; 
	private static final String VALID_EMAIL = "eve.holt@reqres.in";
	private static final String VALID_PASSWORD = "cityslicka";
	private static final String INVALID_EMAIL = "TW";
	private static final String INVALID_PASSWORD = "xxxx";
	private long expectedResponseTime = 5000L;

	@BeforeClass
	public void setup() {
		RestAssured.baseURI = PropertyUtils.getPropertyValue(BASE_URL);
	}

	@Test
    public void testSuccessfulLogin() {
        LoginRequest loginRequest = LoginRequest.builder()
                .email(VALID_EMAIL).password(VALID_PASSWORD).build();
		given()
                .contentType(APPLICATION_JSON)
                .body(loginRequest)
                .when()
                .post(LOGIN_API_PATH)
                .then()
                .statusCode(HTTP_OK_RESPONSE_CODE)
                .contentType(APPLICATION_JSON)
                .body("token", notNullValue());

    }
	
	 @Test
	    public void testFailedLoginWithNoEmailAndUsername() {
		 LoginRequest loginRequest = LoginRequest.builder().build();
	        given()
	                .contentType(APPLICATION_JSON)
	                .body(loginRequest)
	                .when()
	                .post(LOGIN_API_PATH)
	                .then()
	                .statusCode(HTTP_BAD_REQUEST_RESPONSE_CODE)
	                .contentType(APPLICATION_JSON)
	                .body("error", equalTo("Missing email or username"));
	    }
	   
		@Test
		public void testForResponseTimeForValidLoginScenario() {
			LoginRequest loginRequest = LoginRequest.builder()
	                .email(VALID_EMAIL).password(VALID_PASSWORD).build();
			given()
	                .contentType(APPLICATION_JSON)
	                .body(loginRequest)
	                .when()
	                .post(LOGIN_API_PATH)
	                .then()
	                .statusCode(HTTP_OK_RESPONSE_CODE)
	                .contentType(APPLICATION_JSON)
	                .time(lessThan(expectedResponseTime));
		}
		
		@Test
		public void testForResponseTimeForInvalidLoginScenario() {
			 LoginRequest loginRequest = LoginRequest.builder().build();
		        given()
		                .contentType(APPLICATION_JSON)
		                .body(loginRequest)
		                .when()
		                .post(LOGIN_API_PATH)
		                .then()
		                .statusCode(HTTP_BAD_REQUEST_RESPONSE_CODE)
		                .time(lessThan(expectedResponseTime));
		}
		
	 
	   @Test
	    public void testFailedLoginWithNoPassword() {
		   LoginRequest loginRequest = LoginRequest.builder()
	                .email(VALID_EMAIL).build();
	        given()
	                .contentType(APPLICATION_JSON)
	                .body(loginRequest)
	                .when()
	                .post(LOGIN_API_PATH)
	                .then()
	                .statusCode(HTTP_BAD_REQUEST_RESPONSE_CODE)
	                .body("error", equalTo("Missing password"));
	              
	    }
	   
	   @Test
	    public void testFailedLoginWithNoEmail() {
		   LoginRequest loginRequest = LoginRequest.builder()
	                .password(VALID_PASSWORD).build();
	        given()
	                .contentType(APPLICATION_JSON)
	                .body(loginRequest)
	                .when()
	                .post(LOGIN_API_PATH)
	                .then()
	                .statusCode(HTTP_BAD_REQUEST_RESPONSE_CODE);
	              
	    }
	   
	   @Test
	    public void testFailedLoginWithInvalidEmail() {
		   LoginRequest loginRequest = LoginRequest.builder()
	                .email(INVALID_EMAIL).password(VALID_PASSWORD).build();
	        given()
	                .contentType(APPLICATION_JSON)
	                .body(loginRequest)
	                .when()
	                .post(LOGIN_API_PATH)
	                .then()
	                .statusCode(HTTP_BAD_REQUEST_RESPONSE_CODE)
	                .body("error", equalTo("user not found"));
	    }
	   

	   //Note : Ideally test should fail in case of invalid password, but as per working on website, below test has been written	   
	   @Test
	    public void testFailedLoginWithInvalidPassword() {
		   LoginRequest loginRequest = LoginRequest.builder()
	                .email(VALID_EMAIL).password(INVALID_PASSWORD).build();
	        given()
	                .contentType(APPLICATION_JSON)
	                .body(loginRequest)
	                .when()
	                .post(LOGIN_API_PATH)
	                .then()
	                .statusCode(HTTP_OK_RESPONSE_CODE)
	                .contentType(APPLICATION_JSON)
	                .body("token", notNullValue());
	    }
}
