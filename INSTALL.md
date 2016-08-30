Private Key and Public key need to be in resources folder for signing and verification of tokens.
File paths are configured in application.

- Generate Cert
- Get Private Keys in PCKS8 format
- Get Public Keys in DER format

openssl genrsa -out cert.pem 1024
openssl pkcs8 -topk8 -inform PEM -outform PEM -in cert.pem -out private_key.pem -nocrypt
openssl rsa -in cert.pem -pubout -outform DER -out public_key.der

sudo keytool -import -alias nordstrom.ldap -file ldap.crt -keystore /Library/Java/JavaVirtualMachines/jdk1.8.0_73.jdk/Contents/Home/lib/security/cacerts

Install Certs for LDAP 
https://github.com/escline/InstallCert
Need to import the entire cert chain to get SSL validation to work

Use keytool to export and import the right certs (something like this)
keytool -exportcert -alias {alias} -keystore jssecacerts -storepass {password} -file {certname}.cer
sudo keytool -importcert -alias {alias} -keystore /usr/bin/jdk1.8.0_65/jre/lib/security/cacerts -storepass {password} -file {certname}.cer