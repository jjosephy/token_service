package com.tokenservice.tokenprovider;

import java.security.interfaces.RSAPublicKey;

import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.JWSVerifier;

public interface RSAKeyProvider {
    RSAPublicKey getPublicKey();
    JWSSigner getSigner();
    JWSVerifier getVerifier();
}
