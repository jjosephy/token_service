#!/bin/bash

response=$(curl -s --noproxy localhost https://localhost:8443/token  -H "Content-Type: application/json" \
                -H "API-VERSION: 1" -v -k -X POST -d '{"authType":"Mobile","applicationId":"C452AF6C-FB4B-4556-AE55-F6B08270F898","apiKey":"89asdfas8998asdf","mobileId":"555-555-5555","id":"95ed1aaf-e7be-419d-9584-ac0611983f06"}')
printf '\n'
echo '------------------TOKEN-------------------'
echo $response
printf '\n'

echo '---------Authenticating Token-------------'

response=$(curl -s --noproxy localhost https://localhost:8443/auth --header "Authorization: Bearer $response" -H "api-version: 1" -v -k)

echo $response
printf '\n'
