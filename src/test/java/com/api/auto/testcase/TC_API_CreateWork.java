package com.api.auto.testcase;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.api.auto.utils.PropertiesFileUtils;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;

public class TC_API_CreateWork {
	private String token;
	private Response response;
	private ResponseBody responseBody;
	private JsonPath jsonBody;

	private String myWork = "tester";
	private String myExperience = "2 years";
	private String myEducation = "university";

	@BeforeClass
	public void init() {
		String baseURL = PropertiesFileUtils.getProperty("baseURL");
		String basePath = PropertiesFileUtils.getProperty("basePath_create_work");
		String token = PropertiesFileUtils.getToken();

		RestAssured.baseURI = baseURL;
		RestAssured.basePath = basePath;

		Map<String, Object> body = new HashMap<String, Object>();
		body.put("nameWork", myWork);
		body.put("experience", myExperience);
		body.put("education", myEducation);

		RequestSpecification request = RestAssured.given().contentType(ContentType.JSON).header("token", token)
				.body(body);

		response = request.post();
		responseBody = response.body();
		jsonBody = response.jsonPath();

		System.out.println(" " + responseBody.asPrettyString());

	}

	@Test(priority = 0)
	public void TC01_Validate201Created() {
		assertEquals(response.getStatusCode(), 201);
	}

	@Test(priority = 1)
	public void TC02_ValidateWorkId() {
		assertTrue(responseBody.asString().contains("id"));
	}

	@Test(priority = 2)
	public void TC03_ValidateNameOfWorkMatched() {
		assertEquals(jsonBody.getString("nameWork"), myWork);

	}

	@Test(priority = 3)
	public void TC03_ValidateExperienceMatched() {
		assertEquals(jsonBody.getString("experience"), myExperience);

	}

	@Test(priority = 4)
	public void TC03_ValidateEducationMatched() {
		assertEquals(jsonBody.getString("education"), myEducation);
	}

}
