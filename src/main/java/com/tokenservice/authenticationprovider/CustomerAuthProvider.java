package com.tokenservice.authenticationprovider;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import com.tokenservice.model.AuthenticationModel;

/**
 * Authentication Provider to authenticate against a customer Id.
 * 
 * @author q4vy
 *
 */
public class CustomerAuthProvider 
	extends AuthenticationProviderBase 
	implements AuthenticationProvider {

	/**
	 * Set of Ids thats are valid, used to authenticate customerIds
	 */
	Set<UUID> ids = new HashSet<UUID>(Arrays.asList(new UUID[] {
		UUID.fromString("AC98D531-1049-4727-B2F8-728C2A13285E")
	}));
	
	/**
	 * Override method for authenticating customerIds
	 */
	@Override
	public boolean isAuthenticated(AuthenticationModel model) {
		
		if(model.getCustomerId() == null) {
			throw new IllegalArgumentException("customerId");
		}
		
		if (!super.isAuthenticated(model)) {
			return false;
		}
		
		if (ids.contains(model.getCustomerId())) {
			return true;
		}
		
		return false;
	}
}
