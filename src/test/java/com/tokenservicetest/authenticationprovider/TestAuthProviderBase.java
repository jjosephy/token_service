package com.tokenservicetest.authenticationprovider;

import com.tokenservice.authenticationprovider.AuthenticationProvider;
import com.tokenservice.model.AuthenticationModel;
import com.tokenservicetest.constants.TokenServiceTestConstants;

public abstract class TestAuthProviderBase 
	implements AuthenticationProvider {
	
	public TestAuthProviderBase() {
		
	}
	
	@Override
	public boolean isAuthenticated(AuthenticationModel context) {
		if (context == null) {
			throw new IllegalArgumentException("context");
		}
		
		return true;
	}
}
