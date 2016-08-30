package com.tokenservice.authenticationprovider;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import com.tokenservice.model.AuthenticationModel;

/**
 * Base class to provide authentication validations for all authentication providers. Individual
 * providers can extend this class and just call the base to ensure ApiKey and AppId 
 * authentication occurs on each request. 
 * 
 * @author q4vy
 *
 */
public abstract class AuthenticationProviderBase {
	
	Set<String> apiKeys = new HashSet<String>(Arrays.asList(new String[] {
		"234sfdsdfssdfsws", 
		"89asdfas8998asdf"
	}));
	
	Set<UUID> appIds = new HashSet<UUID>(Arrays.asList(new UUID[] {
		UUID.fromString("C452AF6C-FB4B-4556-AE55-F6B08270F898"), 
		UUID.fromString("BE7D241E-EBC5-450F-B34C-E9D5F663EB4A")
	}));
	
	public AuthenticationProviderBase() {
		
	}
	
	public boolean isAuthenticated(AuthenticationModel model) {
		
		if (model == null) {
			throw new IllegalArgumentException("model");
		}
		
		return apiKeys.contains(model.getApiKey().toLowerCase()) 
				&& appIds.contains(model.getApplicationId());
	}
}
