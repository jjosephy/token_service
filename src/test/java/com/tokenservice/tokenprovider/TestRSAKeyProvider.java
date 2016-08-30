package com.tokenservice.tokenprovider;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import com.tokenservice.tokenprovider.RSAKeyProvider;

@Component
@Primary
public class TestRSAKeyProvider implements RSAKeyProvider {
	
	private KeyPairGenerator keyGenerator;
	private RSAPrivateKey privateKey;
	private RSAPublicKey publicKey;
	private JWSSigner signer;
	private JWSVerifier verifier;
	
	public TestRSAKeyProvider() {
		try {
			
			this.keyGenerator = KeyPairGenerator.getInstance("RSA");
			this.keyGenerator.initialize(1024);
			KeyPair kp = this.keyGenerator.genKeyPair();
			this.publicKey = (RSAPublicKey)kp.getPublic();
			this.privateKey = (RSAPrivateKey)kp.getPrivate();
			this.signer = new RSASSASigner(this.privateKey);
			this.verifier = new RSASSAVerifier(this.publicKey);
		} catch (NoSuchAlgorithmException nsEx) {
			// TODO: figure out how to handle this
			this.signer = null;
			this.publicKey = null;
			this.verifier = null;
		}
	}
	
	@Override
	public RSAPublicKey getPublicKey() {
		return this.publicKey;
	}
	
	@Override
	public JWSSigner getSigner() {
		return this.signer;
	}
	
	@Override
	public JWSVerifier getVerifier() {
		return this.verifier;
	}

}
