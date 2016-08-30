package com.tokenservicetest.testauthenticationprovider;

import java.util.HashMap;
import java.util.Map;

import com.tokenservice.authenticationprovider.AuthenticationProvider;
import com.tokenservice.authenticationprovider.AuthenticationProviderBase;
import com.tokenservice.model.AuthenticationModel;

public class TestOnlineAuthProvider 
	extends AuthenticationProviderBase 
	implements AuthenticationProvider {
	
Map<String,String> users = new HashMap<String, String>();
	
	public TestOnlineAuthProvider() {
		users.put("t", "t");
	}
	
	@Override
	public boolean isAuthenticated(AuthenticationModel model) {
		
		if (!super.isAuthenticated(model)) {
			return false;
		}
		
		if (this.users.containsKey(model.getUserName().toUpperCase())) {
			return this.users.containsKey(model.getPassword().toLowerCase());
		}
		
		return false;
	}
}


