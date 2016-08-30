package com.tokenservicetest;

import static org.junit.Assert.*;

import java.util.Map;
import java.util.UUID;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tokenservice.TokenServiceApplication;
import com.tokenservice.configuration.AuthType;
import com.tokenservice.contract.AuthContractV1;
import com.tokenservice.contract.ErrorContract;
import com.tokenservice.authenticationprovider.AuthenticationProvider;
import com.tokenservicetest.authenticationprovider.TestCustomerAuthProvider;
import com.tokenservicetest.authenticationprovider.TestLDAPProvider;
import com.tokenservicetest.constants.TokenServiceTestConstants;

import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = TokenServiceApplication.class)
@WebAppConfiguration
@IntegrationTest("server.port:9000")
@ActiveProfiles(profiles = "unittest")
public class ApplicationTests {
	
	private final String baseUri = "http://localhost:9000";
	
	@Before
	public void setUp() {
		TokenServiceApplication.getServiceLocator().setProvider("customer", new TestCustomerAuthProvider());
		//TokenServiceApplication.getServiceLocator().setProvider("ldap", new TestLDAPProvider());
		TokenServiceApplication.getServiceLocator().setProvider("online", new TestLDAPProvider());
	}
	
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
		
		RestTemplate restTemplate = new RestTemplate(new HttpComponentsClientHttpRequestFactory());
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
		
		ObjectMapper mapper = new ObjectMapper();
		try {
			ErrorContract error = mapper.readValue(ex.getResponseBodyAsString(), ErrorContract.class);
			assertTrue("Invalid Code", error.getCode() == 1000);
			assertTrue("Invalid Message", error.getMessage().equalsIgnoreCase("Unsupported Version"));
			assertTrue("Invalid TimeStamp", error.getTimeStamp().toString().length() > 0);
		} catch (Exception e) {
			fail("Failed deserializing error" + e.getMessage());
		}
	}
	
	@Test
	public void Test_Success_GenerateToken_Valid() {
		
		String token = this.makeTokenRequest(AuthType.Mobile, null);
		System.out.println(token);
		
		String s = this.makeAuthRequest(token);
		System.out.println(s);
	}
	
	@Test
	public void Test_Success_CustomerAuth_MockProvider() {
		String response = makeTokenRequest(AuthType.Customer, null);
		assertTrue("token is empty", response.length() != 0);
		
	}
	
	public void Test_Success_LDAPAuth_MockProvider() {
		String response = makeTokenRequest(AuthType.LDAP, null);
		assertTrue("token is empty", response.length() != 0);
	}
	
	public void Test_Success_OnlineAuth_MockProvider() {
		String response = makeTokenRequest(AuthType.Online, null);
		assertTrue("token is empty", response.length() != 0);
	}
	
	@Ignore ("intergrated into ldap")
	@Test
	public void Test_Success_LDAPAuth() {
		final AuthContractV1 contract = this.getAuthContractV1();
		contract.setUserName("user");
		contract.setPassword("password");
		
		final String response = makeTokenRequest(AuthType.LDAP, contract);
		assertTrue("token is empty", response.length() != 0);
	}
	
	private String makeAuthRequest(String token) {
		return this.makeRequest("auth", token, HttpMethod.GET, null, AuthType.None);
	}
	
	private String makeTokenRequest(AuthType authType, AuthContractV1 contract) {
		return this.makeRequest("token", null, HttpMethod.POST, contract, authType);
	}
	
	private String makeRequest(
			String uriStem, 
			String token,
			HttpMethod method, 
			AuthContractV1 contract, 
			AuthType authType) {
		HttpHeaders headers = new HttpHeaders();
		headers.set("api-version", "1.0");
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		if (token != null && token.length() > 0) {
			headers.set("Authorization", "Bearer " + token);
		}
		
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> response = null;
		HttpEntity<AuthContractV1> entity = null;
		
		if (contract == null && method == HttpMethod.GET) {
			entity = new HttpEntity<AuthContractV1>(null, headers);
		} else if (contract == null) {
			contract = this.getAuthContractV1();
			entity = new HttpEntity<AuthContractV1>(contract, headers);
		} else {
			entity = new HttpEntity<AuthContractV1>(contract, headers);
		}
		
		if (contract != null) {
			contract.setAuthType(authType);
		}
		
		try {
			response = restTemplate.exchange(
					String.format("%s/%s", baseUri, uriStem), 
					method, 
					entity, 
					String.class);
		} catch (HttpClientErrorException e) {
			throw e;
		}
		
		return response.getBody();
	}
	
	private AuthContractV1 getAuthContractV1() {
		AuthContractV1 c = new AuthContractV1();
		c.setAuthType(AuthType.Mobile);
		c.setApplicationId(UUID.fromString("C452AF6C-FB4B-4556-AE55-F6B08270F898"));
		c.setUserName("test user");
		c.setPassword("test password");
		c.setApiKey("89asdfas8998asdf");
		c.setMobileId("555-555-5555");
		c.setCustomerId(UUID.fromString(TokenServiceTestConstants.CustomerId));
		return c;
	}
}