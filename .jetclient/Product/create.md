```toml
name = 'create'
method = 'POST'
url = 'http://localhost:8080/product'
sortWeight = 3000000
id = '6549c44b-68ba-4716-b98e-dde3f9fd77bf'

[auth.bearer]
token = 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJiQGIuY29tIn0.kmNLtBITe-MsZX8nGX0TXfuU2vpcEH7B7DL1N_nESbg'

[body]
type = 'JSON'
raw = '''
{
  "name" : "Rob",
  "description" : "écran 4k",
  "price" : 1,
  "code" : "243",
  "etat" : {
    "id" : 1
  },
  "label" : [
    {
      "id" : 1
    },
    {
      "id" : 4
    }
  ]
}'''
```
