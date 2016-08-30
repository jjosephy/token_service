package com.tokenservice.authenticationprovider;

import com.tokenservice.model.AuthenticationModel;

public interface AuthenticationProvider {
	public boolean isAuthenticated(AuthenticationModel context);
}
