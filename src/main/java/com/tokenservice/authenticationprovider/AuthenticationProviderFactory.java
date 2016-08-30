package com.tokenservice.authenticationprovider;
import com.tokenservice.model.AuthenticationModel;

/**
 *
 * @author Jason Josephy
 */
public class AuthenticationProviderFactory {
	public static boolean authenticate(AuthenticationModel model) {
		AuthenticationProvider provider = null;
		switch (model.getAuthType()) {
			case Customer: 
				provider = AuthProviderLocator.getInstance().getProvider("customer");
				break;
			case Mobile: 
				provider = AuthProviderLocator.getInstance().getProvider("mobile");
				break;
			case None:
				provider = AuthProviderLocator.getInstance().getProvider("default");
				break;
			case Online:
				provider = AuthProviderLocator.getInstance().getProvider("online");
				break;
			default:
				return false;
		}
		
		if (provider == null) {
			return false;
		}
		return provider.isAuthenticated(model);
	}
}
