package com.tokenservice.authentication;

import java.util.Date;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jwt.JWT;
import com.nimbusds.jwt.ReadOnlyJWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import com.tokenservice.exception.InvalidSignatureException;
import com.tokenservice.exception.InvalidTokenException;
import com.tokenservice.exception.TokenExpiredException;
import com.tokenservice.tokenprovider.JWTProvider;

public class TokenAuthenticationManager implements AuthenticationManager {
	
	public TokenAuthenticationManager() {
		
	}

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		AuthToken jwtToken = (AuthToken) authentication;
		JWT jwt = jwtToken.getJwt();
		handleSignedToken((SignedJWT) jwt);
		
		Date referenceTime = new Date();
		ReadOnlyJWTClaimsSet claims = jwtToken.getClaims();
		
		Date expirationTime = claims.getExpirationTime();
		if (expirationTime == null || expirationTime.before(referenceTime)) {
		    throw new TokenExpiredException("The token is expired");
		}
		
		Date notBeforeTime = claims.getNotBeforeTime();
		if (notBeforeTime == null || notBeforeTime.after(referenceTime)) {
		    throw new InvalidTokenException("Not before is after sysdate");
		}
		
		String issuerReference = "https://nordstrom.net";
		String issuer = claims.getIssuer();
		if (!issuerReference.equals(issuer)) {
		    throw new InvalidTokenException("Invalid issuer");
		}
		
		jwtToken.setAuthenticated(true);
		return jwtToken;
	}

	private void handleSignedToken(SignedJWT jwt) {
		try {
			if (!jwt.verify(JWTProvider.getInstance().getVerifier())) {
				throw new InvalidSignatureException("Signature validation failed");
			}
		} catch (JOSEException e) {
			throw new InvalidSignatureException("Signature validation failed");
		}
	}
}


