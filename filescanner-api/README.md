# filescanner-api 

## Development

Benötigt eine nextav-Instanz, die auf 3310 lauscht.

Dafür:

```
/media/veracrypt1/ansible/docker/docker-clamav/docker-compose up -d
```

## Lokaler Microservice für die Entwicklung anderer Anwendungen mit FileUpload

Zuerst muss container nextav gestoppt werden. 

```
/media/veracrypt1/ansible/docker/docker-clamav/docker-compose stop
```

Dann gibt es in ~/bin ein start-filescanner.sh-Script und einen symlink in ~
Damit kann der Microservice gestartet werden. Ist ein docker-compose mit nextav und der filescanner-api.

Oder man navigiert hin:

```
/media/veracrypt1/ansible/docker/filescanner/docker-compose up -d
```

Test mittels cURL oder anderem REST-Client:

```
curl -X 'POST' \
  'http://localhost:9800/files/detection/v1' \
  -H 'accept: application/json' \
  -H 'Content-Type: application/json' \
  -d '{
  "clientId": "k7AxUVYzr1FBAvD8e99orRqKqx4jBwcr7Dmgn5jdBf8J",
  "fileOwner": "ichedoche",
  "upload": {
    "name": "uebel.txt",
    "dataBase64": "WDVPIVAlQEFQWzRcUFpYNTQoUF4pN0NDKTd9JEVJQ0FSLVNUQU5EQVJELUFOVElWSVJVUy1URVNULUZJTEUhJEgrSCo="
  }
}'
```
