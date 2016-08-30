package com.tokenservice.entrypoint;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import com.tokenservice.errorcode.ErrorCodes;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tokenservice.contract.ErrorContract;

public class TokenRequestEntryPoint implements AuthenticationEntryPoint {

	// TODO: add a way to map to codes
	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) 
			throws IOException {
		
		if (authException instanceof AuthenticationException) {
			ErrorContract err = new ErrorContract(ErrorCodes.INVALID_TOKEN, authException.getMessage());
			ObjectMapper m = new ObjectMapper();
			String str = m.writeValueAsString(err);
			response.getWriter().write(str);
			response.setStatus(HttpStatus.UNAUTHORIZED.value());
		} else {
			response.getWriter().write("401");
			response.setStatus(HttpStatus.UNAUTHORIZED.value());
		}
	}
}
