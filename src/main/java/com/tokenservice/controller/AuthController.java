package com.tokenservice.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tokenservice.authentication.AuthToken;

import net.minidev.json.JSONObject;

@RestController
public class AuthController {
	@RequestMapping(value = "/auth") 
	public String user(HttpServletRequest request, HttpServletResponse response) {
		
		SecurityContext context = SecurityContextHolder.getContext();
		Authentication auth = context.getAuthentication();
		AuthToken t = (AuthToken) auth;
		
		JSONObject json = new JSONObject();
		json.putAll(t.getClaims().getAllClaims());
		return json.toJSONString();
	}
}
