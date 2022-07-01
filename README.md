# Mockify

A mocking service to create mock webhooks.</br>
You can create a webhook, setup it's response, and then
test you api calls.

## How to run

```shell
docker-compose up -d
```

## Api docs

```
http://localhost:8080/swagger-ui/index.html
```

## How to use

1. Create a new webhook

```
GET http://localhost:8080/hook/[CUSTOMNANE]
```

response:

```json
{
  "status": "ok"
}
```

2. Modify response

```
PATCH http://localhost:8080/hook/[CUSTOMNANE]/reponse
```

body:

```json 
{
  "animal" : "dog",
  "age": 3,
  "name": "Reksio"
}

```

3. View all events

```
GET http://localhost:8080/hook/[CUSTOMNANE]/events
```

Response:

```json
[
  {
    "request": {
      "method": "GET",
      "body": null,
      "headers": {
        "host": "localhost:8080",
        "connection": "keep-alive",
        "sec-ch-ua": "\" Not A;Brand\";v=\"99\", \"Chromium\";v=\"101\", \"Google Chrome\";v=\"101\"",
        "sec-ch-ua-mobile": "?0",
        "sec-ch-ua-platform": "\"Windows\"",
        "upgrade-insecure-requests": "1",
        "user-agent": "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/101.0.4951.67 Safari/537.36",
        "accept": "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9",
        "sec-fetch-site": "none",
        "sec-fetch-mode": "navigate",
        "sec-fetch-user": "?1",
        "sec-fetch-dest": "document",
        "accept-encoding": "gzip, deflate, br",
        "accept-language": "pl,pl-PL;q=0.9"
      }
    },
    "response": {
      "body": {
        "status": "ok"
      }
    },
    "timestamp": "2022-05-21T07:42:11.066"
  },
  {
    "request": {
      "method": "GET",
      "body": null,
      "headers": {
        "host": "localhost:8080",
        "connection": "keep-alive",
        "sec-ch-ua": "\" Not A;Brand\";v=\"99\", \"Chromium\";v=\"101\", \"Google Chrome\";v=\"101\"",
        "sec-ch-ua-mobile": "?0",
        "sec-ch-ua-platform": "\"Windows\"",
        "upgrade-insecure-requests": "1",
        "user-agent": "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/101.0.4951.67 Safari/537.36",
        "accept": "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9",
        "sec-fetch-site": "none",
        "sec-fetch-mode": "navigate",
        "sec-fetch-user": "?1",
        "sec-fetch-dest": "document",
        "accept-encoding": "gzip, deflate, br",
        "accept-language": "pl,pl-PL;q=0.9"
      }
    },
    "response": {
      "body": {
        "animal": "dog",
        "age": "3",
        "name": "Reksio"
      }
    },
    "timestamp": "2022-05-21T07:58:29.259"
  }
]
```

4. Delete all events for a Hook

```
DELETE http://localhost:8080/hook/[CUSTOMNANE]/events
```
## Supported HTTP Methods

```
GET, POST, PUT, PATCH DELETE
```
