
# robinhood-api
Microservice API for Robinhood (Purple Ventures Co., Ltd.) Test


## Tech Stack

![alt text](https://img.shields.io/badge/Spring_Boot-F2F4F9?style=for-the-badge&logo=spring-boot )
https://spring.io/projects/spring-boot (3.1.9)

![alt text ](https://img.shields.io/badge/MySQL-005C84?style=for-the-badge&logo=mysql&logoColor=white)
https://www.mysql.com (8.0.31)

![alt text <](https://img.shields.io/badge/Docker-2CA5E0?style=for-the-badge&logo=docker&logoColor=white)
https://www.docker.com










## Features

- User API
- Topic API
- Comment API
- API Authentication (Username/Password)
- API Rate-Limiting
- Cross platform deployment by Docker


## Deployment

To deploy this project run on **Docker** 


```bash
docker compose up
```


## Usage/Examples

API URL ex.
```
http://localhost:8080/user/getData
```

API Rate Limit config example in **application.properties** 

use @RateLimiter(name = "backendA") or @RateLimiter(name = "oneTime") on API controller

```
resilience4j.ratelimiter.instances.backendA.limitForPeriod=10
resilience4j.ratelimiter.instances.backendA.limitRefreshPeriod=10s
resilience4j.ratelimiter.instances.backendA.timeoutDuration=0s

resilience4j.ratelimiter.instances.oneTime.limitForPeriod=1
resilience4j.ratelimiter.instances.oneTime.limitRefreshPeriod=5s
resilience4j.ratelimiter.instances.oneTime.timeoutDuration=0s
```

MySql (for Database tool ex.DBeaver)
```
 localhost:5306 
```

- DB Schema
  
![DB](https://github.com/gdvz/robinhood-api/assets/39118712/fbde9d0e-1cd6-42cf-8909-cb6ac3610e11)


Data for test API

```
Username : jakkarin        |     Username : robinhood      
Password : 1234            |     Password : 1234
Role : ROLE_ADMIN          |     Role : ROLE_USER
```


## API Reference
### <ins>User API</ins>
#### - Register User (Open for all user include anonymous)

```http
  POST /user/register
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `username` | `string` | **Required**. Your Username for login |
| `password` | `string` | **Required**. Your Password for login |
| `email` | `string` | **Required**. Your Email |
| `fullName` | `string` | **Required**. Your Full name |
| `appUserRole` | `string` | **Required**. Your Role (ROLE_ADMIN / ROLE_USER  )|

---

#### - Get all User data (Open only ROLE_ADMIN)

```http
  GET /user/getData
```
---
---


### <ins>Topic API</ins>
#### - Get Data all topic (Open for all user ROLE_ADMIN / ROLE_USER)

```http
  GET /topic/getData?page=${page}&pageSize=${pageSize}
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `page` | `int` | **Not Required**. Number of page start from 0 (default 0) |
| `pageSize` | `int` | **Not Required**. Number item on the page (default 3)|

---


#### - Get Data Topic data detail (Open for all user ROLE_ADMIN / ROLE_USER)

```http
  GET /topic/getDetail/${id}
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `id` | `int` | **Required**. Topic Id (DB : topic.id) |

---


#### - Get Data Topic history (Open for all user ROLE_ADMIN / ROLE_USER)

```http
  GET /topic/getHistory/${id}
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `id` | `int` | **Required**. Topic Id (DB : topic.id) |


---


#### - Add Topic (Open for all user ROLE_ADMIN / ROLE_USER)

```http
  POST /topic/add
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `subject` | `string` | **Required**. Your Topic subject |
| `description` | `string` | **Required**. Your Topic description |
| `status` | `string` | **Required**. Your Topic status |

---



#### - Edit Data Topic (Open for all user ROLE_ADMIN / ROLE_USER)

```http
  POST /topic/editData
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `id` | `long` | **Required**. Your Topic id (DB : topic.id) |
| `subject` | `string` | **Required**. Your New Topic subject |
| `description` | `string` | **Not Required**. Your New Topic description |
| `status` | `string` | **Required**. Your New Topic status |

---


#### - Collect Topic (Open for all user ROLE_ADMIN / ROLE_USER)

```http
  GET /topic/collect?id={id}
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `id` | `long` | **Required**. Your Topic id (DB : topic.id) |


---
---


### <ins>Comment API</ins>
#### - Get Comment on Topic (Open for all user ROLE_ADMIN / ROLE_USER)

```http
  GET /comment/getData?topicId=${topicId}
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `topicId` | `long` | **Required**. Your Topic id (DB : comment.topic_id) |


---


#### - Add Comment on Topic (Open for all user ROLE_ADMIN / ROLE_USER)

```http
  POST /comment/add
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `topic_id` | `long` | **Required**. Your Topic id (DB : topic.id) |
| `description` | `string` | **Required**. Your Comment description |


---


#### - Edit Comment on Topic (Open for Owner of comment ROLE_ADMIN / ROLE_USER)

```http
  POST /comment/edit
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `comment_id` | `long` | **Required**. Your Comment id (DB : comment.id) |
| `description` | `string` | **Required**. Your New Comment description |


---


#### - Delete Comment on Topic (Open for Owner of comment ROLE_ADMIN / ROLE_USER)

```http
  POST /comment/delete
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `comment_id` | `long` | **Required**. Your Comment id (DB : comment.id) |

---
---

## Documentation

[Basic Authentication](https://docs.spring.io/spring-security/reference/servlet/authentication/passwords/basic.html)

[Circuit Breaker, Rate Limiter, Retry or Bulkhead.](https://resilience4j.readme.io/docs/getting-started)

[Spring boot with docker](https://www.docker.com/blog/kickstart-your-spring-boot-application-development/)





## Authors

- Mr. Jakkarin Saelaw 
- EMAIL : mr.chakkarin@hotmail.com
- [@gdvz](https://github.com/gdvz) 

