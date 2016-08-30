#!/bin/bash
mvn clean install

install/cert.sh
install/keystore.sh

#cp src/main/resources/private_key.pem target/cert
#cp src/main/resources/public_key.der target/cert
#/Users/q4vy/Source/java/token_service/src/main/resources

java -jar target/token-service-0.0.1-SNAPSHOT.jar
