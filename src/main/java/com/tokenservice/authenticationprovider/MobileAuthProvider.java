package com.tokenservice.authenticationprovider;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import com.tokenservice.model.AuthenticationModel;

/**
 * Mobile Authentication Provider to enable authentication via a Mobile Id. 
 * 
 * @author q4vy
 *
 */
public class MobileAuthProvider 
	extends AuthenticationProviderBase 
	implements AuthenticationProvider {
	
	Set<String> numbers = new HashSet<String>(Arrays.asList(new String[] {
		"555-555-5555", 
		"555-555-5556"
	}));
	
	/**
	 * Default Constructor
	 */
	public MobileAuthProvider() {
		
	}
	
	/**
	 * Implementation of Authentication Provider Interface for Mobile Authentication
	 */
	@Override
	public boolean isAuthenticated(AuthenticationModel model) {
		
		if (!super.isAuthenticated(model)) {
			return false;
		}
		
		if (this.numbers.contains(model.getMobileId())) {
			return true;
		}
		
		return false;
	}
}
