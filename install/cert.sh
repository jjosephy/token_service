#!/bin/bash
#set this path for certs
CERTPATH='/Users/q4vy/Source/java/token_service/src/main/resources'

openssl genrsa -out "$CERTPATH/cert.pem" 1024
openssl pkcs8 -topk8 -inform PEM -outform PEM -in "$CERTPATH/cert.pem" -out "$CERTPATH/private_key.pem" -nocrypt
openssl rsa -in "$CERTPATH/cert.pem" -pubout -outform DER -out "$CERTPATH/public_key.der"
