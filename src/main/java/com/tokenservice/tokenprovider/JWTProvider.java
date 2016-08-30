package com.tokenservice.tokenprovider;

import java.security.interfaces.RSAPublicKey;
import java.util.Base64;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

@Component
public class JWTProvider {
	private volatile static JWTProvider instance = null;
	private RSAKeyProvider keyProvider = null;
	
	private JWTProvider() {
	}
	
	@Autowired
	private JWTProvider(RSAKeyProvider provider) {
		keyProvider = provider;
		instance = this;
	}
	
	public static JWTProvider getInstance() {
		return instance;
	}
	
	public String GenerateToken() throws JOSEException {
		JWTClaimsSet claimsSet = new JWTClaimsSet();
		claimsSet.setSubject("nordstrom");
		claimsSet.setIssuer("https://nordstrom.net");
		
		Date expiration = new Date();
		//expiration.setTime(expiration.getTime()+ (30 * 60000));
		expiration.setTime(expiration.getTime()+ (30000));
		claimsSet.setExpirationTime(expiration);
		claimsSet.setNotBeforeTime(new Date());
		
		// Set Custom claims
		claimsSet.setCustomClaim("emp", "0");
		claimsSet.setCustomClaim("mobile", "000-000-0000");
		claimsSet.setCustomClaim("customerId", "123");
		claimsSet.setCustomClaim("auth", "low");
		
		SignedJWT signedJWT = new SignedJWT(new JWSHeader(JWSAlgorithm.RS512), claimsSet);
		signedJWT.sign(this.keyProvider.getSigner());
		
		String serialized = signedJWT.serialize();
		String encoded = Base64.getEncoder().encodeToString(serialized.getBytes());
		return encoded;
	}
	
	public RSAPublicKey getPublicKey() {
		return keyProvider.getPublicKey();
	}
	
	public JWSVerifier getVerifier() {
		return keyProvider.getVerifier();
	}
}
