package com.tokenservice.authenticationprovider;

import com.tokenservice.model.AuthenticationModel;

/**
 * Default Authentication Provider to authenticate just on AppId and ApiKey
 * @author q4vy
 *
 */
public class DefaultAuthProvider 
	extends AuthenticationProviderBase 
	implements AuthenticationProvider {

	@Override
	public boolean isAuthenticated(AuthenticationModel model) {
		
		if (super.isAuthenticated(model)) {
			return true;
		}
		
		return false;
	}
}
