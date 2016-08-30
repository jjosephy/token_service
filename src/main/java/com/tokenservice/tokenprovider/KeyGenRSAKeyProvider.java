package com.tokenservice.tokenprovider;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import com.tokenservice.configuration.ConfigManager;

@Component
@Profile("default")
public class KeyGenRSAKeyProvider implements RSAKeyProvider {
	
	private RSAPrivateKey privateKey;
	private RSAPublicKey publicKey  = null;
	private JWSSigner signer  = null;
	private JWSVerifier verifier  = null;

	public KeyGenRSAKeyProvider() {

		try {
			KeyFactory factory = KeyFactory.getInstance("RSA");
				
			// Get Private Key
			byte[] bytes = this.readFile(ConfigManager.getInstance().getCertPrivateKey());
			String temp = new String(bytes);
			String privKeyPEM = temp.replace("-----BEGIN PRIVATE KEY-----\n", "");
			privKeyPEM = privKeyPEM.replace("-----END PRIVATE KEY-----", "");
			privKeyPEM = privKeyPEM.replaceAll("\\s", "");
			bytes = Base64.getDecoder().decode(privKeyPEM);
			PKCS8EncodedKeySpec privSpec = new PKCS8EncodedKeySpec(bytes);
			this.privateKey = (RSAPrivateKey)factory.generatePrivate(privSpec);
			
			// Get the public key
			bytes = this.readFile(ConfigManager.getInstance().getCertPublicKey());
			X509EncodedKeySpec spec = new X509EncodedKeySpec(bytes);
			this.publicKey = (RSAPublicKey) factory.generatePublic(spec);
			this.signer = new RSASSASigner(privateKey);
			this.verifier = new RSASSAVerifier(this.publicKey);
			
		} catch (NoSuchAlgorithmException nsEx) {
			// TODO: handle
			nsEx.printStackTrace();
		} catch (IOException ioEx) {
			//TODO: handle
			ioEx.printStackTrace();
		} catch (InvalidKeySpecException kEx) {
			// TODO handle
			kEx.printStackTrace();
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
	
	private byte[] readFile(String fileName) throws IOException {
		DataInputStream dis = null;
		try {
			//ClassPathResource res = new ClassPathResource(fileName);
			File file = new File(fileName);
			dis = new DataInputStream(new FileInputStream(file));
			byte[] bytes = new byte[(int)file.length()];
			dis.read(bytes);
			return bytes;
		} catch (Exception e) {
			throw e;
		} finally {
			if (dis != null) {
				dis.close();
			}
		}
	}
}
