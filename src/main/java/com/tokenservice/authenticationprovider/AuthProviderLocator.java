package com.tokenservice.authenticationprovider;

import java.util.HashMap;
import java.util.Map;

/**
 * AuthProviderLocator is a simple service locator to set up a framework for 
 * Authentication Providers. Using a simple map instead of Spring IOC to make injection 
 * framework a bit more lightweight.
 * 
 * @author q4vy
 *
 */
public class AuthProviderLocator {
	/**
	 * Instance of AuthProviderLocator
	 */
	private static AuthProviderLocator instance;
	
	/**
	 * HashMap of all supported providers
	 */
	private Map<String, AuthenticationProvider> providers; 
	
	/**
	 * Default private ctor for creating the provider map.
	 */
	private AuthProviderLocator() {
		this.providers = new HashMap<String, AuthenticationProvider>();
		
		// Add default providers
		this.providers.put("mobile", new MobileAuthProvider());
		this.providers.put("customer", new CustomerAuthProvider());
		this.providers.put("online", new OnlineAuthProvider());
		this.providers.put("default", new DefaultAuthProvider());
	}
	
	/**
	 * Singleton getter for getting an instance of the AuthProviderLocator
	 * @return
	 */
	public static AuthProviderLocator getInstance() {
		if (instance == null) {
			synchronized(AuthProviderLocator.class) {
				if (instance == null) {
					instance = new AuthProviderLocator();
				}
			}
		}
		
		return instance;
	}
	
	/**
	 * Gets an instance of a provider from the map.
	 * @param name
	 * @return
	 */
	public AuthenticationProvider getProvider(String name) {
		if (!this.providers.containsKey(name)) {
			return null;
		}
		
		return this.providers.get(name);
	}
	
	/**
	 * Sets a provider in the locator map. This can be used for testing via property injection
	 * @param name - the name of the provider
	 * @param provider - the instance of the provider to use for the given key
	 */
	public void setProvider(String name, AuthenticationProvider provider) {
		this.providers.put(name, provider);
	}
}
