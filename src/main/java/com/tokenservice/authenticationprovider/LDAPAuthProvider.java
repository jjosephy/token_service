package com.tokenservice.authenticationprovider;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;
import com.tokenservice.configuration.ConfigManager;
import com.tokenservice.model.AuthenticationModel;

/**
 * LDAP Authentication Provider to enable authentication via Active Directory via User Name and Password.
 * User Name is in the form of {networkid}@nordstrom.net e.g. q4vy@nordstrom.net.
 * Does a simple LDAP bind on provided user name.
 * 
 * @author q4vy
 *
 */
public class LDAPAuthProvider 
	extends AuthenticationProviderBase 
	implements AuthenticationProvider {
	
	public LDAPAuthProvider() {
		
	}
	
	public boolean isAuthenticated(AuthenticationModel model) {
		
		if (!super.isAuthenticated(model)) {
			return false;
		}
		
		if (isAuthenticated(model.getUserName(), model.getPassword())) {
			return true;
		}
		
		return false;
	}
	
	/**
	 * isAuthenticated performs a simple bind against an Directory to authenticate based off of user name and password
	 * @param userName : the user name to bind against
	 * @param password : the credential to bind against
	 * @return : true if the bind is successful otherwise false
	 */
	private boolean isAuthenticated(String userName, String password) {
				
		final String ldapAdServer = ConfigManager.getInstance().getLDAPHost();
		final Hashtable<String, Object> env = new Hashtable<String, Object>();
		env.put(Context.SECURITY_AUTHENTICATION, "simple");
		env.put(Context.SECURITY_PRINCIPAL, userName);
		env.put(Context.SECURITY_CREDENTIALS, password);
		env.put(Context.SECURITY_PROTOCOL, "ssl");
		env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
		env.put(Context.PROVIDER_URL, ldapAdServer);
		
		LdapContext ctx = null;
		try {
			ctx = new InitialLdapContext(env, null);
			return true;
		} catch (NamingException e) {
			System.out.println(e.getMessage());
			return false;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return false;
		}
		finally {
			if (ctx != null) {
				try {
					ctx.close();
				} catch (NamingException e) {
					// TODO: log this exception
					System.out.println(e.getMessage());
				}
			}
		}
	}
}
