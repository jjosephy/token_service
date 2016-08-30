package com.tokenservice.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tokenservice.contract.ErrorContract;
import com.tokenservice.tokenprovider.JWTProvider;

@RestController
public class TokenController {
	@RequestMapping(value = "/token") 
	public String token(HttpServletRequest request, HttpServletResponse response) {
		try {
			return JWTProvider.getInstance().GenerateToken();
		} catch(Exception e) {
			String msg = e.getMessage() == null ? e.toString() : e.getMessage();
			ErrorContract er = new ErrorContract(2000, msg);
			ObjectMapper m = new ObjectMapper();
			try {
				String str = m.writeValueAsString(er); 
				return str;
			} catch (JsonProcessingException jEx) {
				//TODO: handle
			}
			
			// TODO: replace this with message writer
			return "exception"; 
		}
	}
}
