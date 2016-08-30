package com.tokenservicetest;

import static org.junit.Assert.*;

import java.util.Map;
import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.tokenservice.TokenServiceApplication;
import com.tokenservice.configuration.AuthType;
import com.tokenservice.contract.AuthContractV1;

import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = TokenServiceApplication.class)
@WebAppConfiguration
@IntegrationTest("server.port:9000")
@ActiveProfiles(profiles = "unittest")
public class ApplicationTests {
	
	private final String baseUri = "http://localhost:9000";
	
	@Test
	public void Test_Failure_InvalidVersionHeader() {
		
		RestTemplate restTemplate = new RestTemplate();
		HttpClientErrorException ex = null;
		HttpHeaders headers = new HttpHeaders();
		headers.set("api-version", "ABC");
		HttpEntity<?> entity = new HttpEntity<Map<String,String>>(headers);
		
		try {
			restTemplate.exchange(
					String.format("%s/token", baseUri), 
					HttpMethod.POST, 
					entity, 
					String.class);
		} catch (HttpClientErrorException e) {
			ex = e;
		}
		
		assertTrue("expected exception is null", ex != null);
		assertTrue("incorrect status code", ex.getStatusCode() == HttpStatus.UNAUTHORIZED);
	}
	
	@Test
	public void Test_Failure_NoVersionHeader() {
		
		RestTemplate restTemplate = new RestTemplate();
		HttpClientErrorException ex = null;
		try {
			restTemplate.exchange(
					String.format("%s/token", baseUri), 
					HttpMethod.POST, 
					null, 
					String.class);
		} catch (HttpClientErrorException e) {
			ex = e;
		}
		
		assertTrue("expected exception is null", ex != null);
		assertTrue("incorrect status code", ex.getStatusCode() == HttpStatus.UNAUTHORIZED);
		
		// TODO: figure out why exception body not populated
		//String response = ex.getResponseBodyAsString();
		
	}
	
	@Test
	public void Test_Success_GenerateToken_Validate() {
		HttpHeaders headers = new HttpHeaders();
		headers.set("api-version", "1.0");
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> response = null;
		HttpEntity<AuthContractV1> body_entity = new HttpEntity<AuthContractV1>(this.getAuthContractV1(), headers);
		
		try {
			response = restTemplate.exchange(
					String.format("%s/token", baseUri), 
					HttpMethod.POST, 
					body_entity, 
					String.class);
		} catch (HttpClientErrorException e) {
			String er = e.getMessage();
			System.out.println(er);
			fail("exception occured trying to create token");
		}
		
		String token = response.getBody();
		System.out.println(response);
		
		headers.set("Authorization", "Bearer " + token);
		
		HttpEntity<?> entity = new HttpEntity<Map<String,String>>(headers);
		try {
			response = restTemplate.exchange(
					String.format("%s/auth", baseUri), 
					HttpMethod.GET, 
					entity, 
					String.class);
		} catch (HttpClientErrorException e) {
			String er = e.getMessage();
			System.out.println(er);
			fail("exception occured trying to auth token");
		}
		
		String s = response.getBody();
		System.out.println(s);
	}
	
	private AuthContractV1 getAuthContractV1() {
		AuthContractV1 c = new AuthContractV1();c.setAuthType(AuthType.Mobile);
		c.setApplicationId(UUID.fromString("C452AF6C-FB4B-4556-AE55-F6B08270F898"));
		c.setUserName("test user");
		c.setPassword("test password");
		c.setApiKey("89asdfas8998asdf");
		c.setMobileId("555-555-5555");
		return c;
	}
}