keytool -genkey -noprompt \
 -storetype PKCS12 \
 -keyalg RSA \
 -keysize 2048 \
 -alias token_service  \
 -dname "CN=localhost, OU=auth.nord.com, O=nordstrom, L=Seattle, S=Washington, C=US" \
 -keystore token_service.p12 \
 -validity 3650

cp token_service.p12 ../../../
