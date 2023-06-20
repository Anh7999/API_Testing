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

public class TC_API_Login {
	private String account;
	private String password;
	private Response response;
	private ResponseBody responseBody;
	private JsonPath jsonBody;
	
	@BeforeClass
	public void init() {
		String baseUrl =  PropertiesFileUtils.getProperty("baseURL");
		String basePath = PropertiesFileUtils.getProperty("basePath_login");
		account = PropertiesFileUtils.getProperty("account_login");
		password = PropertiesFileUtils.getProperty("password_login");
		
		RestAssured.baseURI = baseUrl;
		RestAssured.basePath =basePath;
		
		Map<String, Object> body = new HashMap<String, Object>();
		body.put("account", account);
		body.put("password", password);
		
		RequestSpecification request = RestAssured.given()
				.contentType(ContentType.JSON)
				.body(body);
		response = request.post();
		responseBody = response.body();
		jsonBody = response.jsonPath();
		
		System.out.println(responseBody.asPrettyString());
	}
	
	@Test(priority = 0)
	public void TC01_ValidateStatus() {
		assertEquals(response.getStatusCode(), 200);
	}
	
	@Test(priority = 1)
	public void TC02_ValidateMessage() {
		String act_message = jsonBody.getString("message");
		String exp_message = "Đăng nhập thành công";
		
		assertTrue(responseBody.asString().contains("message"));
		assertEquals(act_message, exp_message);
	}
	
	@Test(priority = 2)
	public void TC03_ValidateToken() {
		assertTrue(responseBody.asString().contains("token"));
		PropertiesFileUtils.setToken("token", jsonBody.getString("token"));
	}
	@Test(priority = 3)
	public void TC04_ValidateUser() {
		assertTrue(responseBody.asString().contains("user"));
	}
	
	@Test(priority = 4)
	public void TC05_ValidateAccount() {
		assertTrue(responseBody.asString().contains("account"));
		assertEquals(account, jsonBody.getString("user.account"));
	}
	
	@Test(priority = 5)
	public void TC06_ValidatePassword() {
		assertTrue(responseBody.asString().contains("password"));
		assertEquals(password, jsonBody.getString("user.password"));
	}
	
	@Test(priority = 6)
	public void TC07_ValidateType() {
		String type = "UNGVIEN";
		assertEquals(jsonBody.getString("user.type"), type);
	}
}
