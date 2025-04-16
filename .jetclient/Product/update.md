```toml
name = 'update'
method = 'PUT'
url = 'http://localhost:8080/product/1'
sortWeight = 5000000
id = '4e2fbdf1-07cf-493c-a12c-bc9f15450d07'

[body]
type = 'JSON'
raw = '''
{
  "id": 1,
  "name": "Asus Zenbook A14",
  "code": "aza14",
  "description": "Ordinateur portable léger avec processeur Snapdragon X Elite",
  "price": 1299.99,
  "etat": {
    "id": 1,
    "name": "neuf"
  },
  "labelList": [
    {
      "id": 1,
      "name": "Promo"
    },
    {
      "id": 3,
      "name": "Fin de série"
    }
  ]
}'''
```
