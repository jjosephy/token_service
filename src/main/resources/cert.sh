openssl genrsa -out cert.pem 1024
openssl pkcs8 -topk8 -inform PEM -outform PEM -in cert.pem -out private_key.pem -nocrypt
openssl rsa -in cert.pem -pubout -outform DER -out public_key.der
