Private Key and Public key need to be in resources folder for signing and verification of tokens.
File paths are configured in application.

- Generate Cert
- Get Private Keys in PCKS8 format
- Get Public Keys in DER format

openssl genrsa -out cert.pem 1024
openssl pkcs8 -topk8 -inform PEM -outform PEM -in cert.pem -out private_key.pem -nocrypt
openssl rsa -in cert.pem -pubout -outform DER -out public_key.der
