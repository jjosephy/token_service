
$url = 'https://localhost:8443/token'

try {
    $r = Invoke-RestMethod -Uri $url -Method Post
    Write-Host $r
}
catch 
{
    $result = $_.Exception
    Write-Host $result
}
#curl -Uri $url


#response=$(curl -s --noproxy localhost https://localhost:8443/token \
#        -H "api-version: 1" -v -k \
#        -H "Content-Type: application/json" \
#        -X POST -d '{"authType":"Mobile","applicationId":"00000000-0000-0001-0000-000000000064","userName":"uname","password":"pwd","apiKey":"89asdfas8998asdf","mobileId":"555-555-5555","id":"95ed1aaf-e7be-419d-9584-ac0611983f06"}')
