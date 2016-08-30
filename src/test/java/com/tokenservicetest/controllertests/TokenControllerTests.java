package com.tokenservicetest.controllertests;

import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import com.tokenservice.controller.TokenController;

public class TokenControllerTests {

	final String BASE_URL = "http://localhost:9000/";

	@Autowired
	private MockMvc mockMvc;

	@Before
	public void setup() throws Exception {
		TokenController instance = new TokenController();
		this.mockMvc = MockMvcBuilders.standaloneSetup(instance).build();
	}

	@Test
	public void testController_success_tokenRequest() throws Exception {
		MockHttpServletRequestBuilder getRequest = get("/token");
		getRequest.header("api-version", "1");
		ResultActions action = mockMvc.perform(getRequest).andExpect(status().isOk());
		MvcResult r = action.andReturn();
		String str = r.getResponse().getContentAsString();
		assertTrue("len is zero", str.length() > 0);
	}
}
