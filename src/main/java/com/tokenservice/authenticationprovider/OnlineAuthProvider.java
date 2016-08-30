package com.tokenservice.authenticationprovider;

import java.util.HashMap;
import java.util.Map;

import com.tokenservice.model.AuthenticationModel;

/**
 * Authentication Provider to authenticate via user name and password
 * @author q4vy
 *
 */
public class OnlineAuthProvider 
	extends AuthenticationProviderBase 
	implements AuthenticationProvider {
	
	/**
	 * Map that contains all valid users
	 */
	Map<String,String> users = new HashMap<String, String>();
	
	public OnlineAuthProvider() {
		users.put("testuser", "password");
		users.put("jjosephy@nordstrom.com", "testpass");
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
