package com.tokenservicetest.authenticationprovider;

import com.tokenservice.model.AuthenticationModel;
import com.tokenservicetest.constants.TokenServiceTestConstants;

public class TestCustomerAuthProvider 
	extends TestAuthProviderBase {

	@Override
	public boolean isAuthenticated(AuthenticationModel context) {		
		boolean auth = super.isAuthenticated(context);
		
		if (context.getCustomerId() == null) {
			throw new IllegalArgumentException("context.customerId");
		}
		
		String str = context.getCustomerId().toString();
		if (str.equalsIgnoreCase(TokenServiceTestConstants.CustomerId)) {
			auth = true;
		}
		
		return auth;
	}

	
}