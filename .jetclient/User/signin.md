```toml
name = 'signin'
method = 'POST'
url = 'http://localhost:8080/signin'
sortWeight = 1000000
id = '034dd8d4-8818-49c1-be2e-fca4eeaa5630'

[auth]
type = 'NO_AUTH'

[body]
type = 'JSON'
raw = '''
{
  "email" : "a@a.com",
  "password" : "root"
}'''
```
