#!/bin/bash

createCert()  {
    echo "Creating cert"
    keytool -genkey -noprompt \
     -storetype PKCS12 \
     -keyalg RSA \
     -keysize 2048 \
     -alias token_service  \
     -dname "CN=localhost, OU=auth.nord.com, O=nordstrom, L=Seattle, S=Washington, C=US" \
     -keystore token_service.p12 \
     -validity 3650 \
     -storepass redjeeps
    #cp token_service.p12 ../
}

FILE='token_service.p12'

if [ -f "$FILE" ];
then
   echo "Deleting $FILE."
   rm -f $FILE
   createCert
else
   createCert
fi
