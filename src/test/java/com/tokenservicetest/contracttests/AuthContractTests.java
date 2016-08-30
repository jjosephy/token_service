package com.tokenservicetest.contracttests;

import static org.junit.Assert.*;

import java.util.UUID;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tokenservice.configuration.AuthType;
import com.tokenservice.contract.AuthContractV1;

public class AuthContractTests {
	
	@Test
	public void TestBasicContract_V1_Success_ValidParams() {
		AuthContractV1 c = new AuthContractV1();
		UUID u = new UUID(1, 100);
		c.setApplicationId(u);
		c.setAuthType(AuthType.Customer);
		c.setPassword("pwd");
		c.setUserName("uname");
		c.setApiKey("89asdfas8998asdf");
		
		ObjectMapper m = new ObjectMapper();
		try {
			String json = m.writeValueAsString(c);
			assertTrue("len is invalid", json.length() > 0);
			System.out.println(json);
		} catch (JsonProcessingException e) {
			fail("json serialization failed");
		}
		
	}
}
