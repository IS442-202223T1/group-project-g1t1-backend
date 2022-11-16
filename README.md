# Backend

[![Build Status](https://travis-ci.org/codecentric/springboot-sample-app.svg?branch=master)](https://travis-ci.org/codecentric/springboot-sample-app)
[![Coverage Status](https://coveralls.io/repos/github/codecentric/springboot-sample-app/badge.svg?branch=master)](https://coveralls.io/github/codecentric/springboot-sample-app?branch=master)
[![License](http://img.shields.io/:license-apache-blue.svg)](http://www.apache.org/licenses/LICENSE-2.0.html)

Minimal [Spring Boot](http://projects.spring.io/spring-boot/) sample app.

## Requirements

For building and running the application you need:

- [JDK 1.8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
- [Maven 3](https://maven.apache.org)

## Installation and Set-Up

### Set-up Local Directories

Clone this repository or download to files to local directory. Open a terminal session and navigate to this application root (`.../group-project-g1t1-backend`)

```bash
cd /path/to/group-project-g1t1-backend
```

### Provide Environment Variables

This application relies on Mail Credentials, Web Server URL and Authentication Secrets to assist in computation logic and data. We need to provide it the following information. Edit the different variables in `.env.example` using any text editor (`vi .env.example`).

1. Navigate to application root
```bash
cd cpa
```
2. Replace `<>` fields with the respective information
3. Rename `.env.example` to `.env`

**Note: `.env` is automatically ignored by git`**

## Running the application locally

There are several ways to run a Spring Boot application on your local machine. One way is to execute the `main` method in the `cpa.src.main.java.com.is442project.cpa.CorporatePassApplication` class from your IDE.

Alternatively you can use the [Spring Boot Maven plugin](https://docs.spring.io/spring-boot/docs/current/reference/html/build-tool-plugins-maven-plugin.html).

Navigate to `.../group-project-g1t1-backend/cpa` and execute the following command:

```shell
mvn spring-boot:run
```

By default, the application runs on port 8080.

## Authentication via JWT

All endpoints (except for `/api/v1/account/create`) require authentication via JWT. This token is issued by the `/login` endpoint.

In order to request a token, a user account must first be created.

```shell
curl --location --request POST 'http://localhost:8080/api/v1/account/create' \
--header 'Content-Type: application/json' \
--data-raw '{
    "email":"new_user@gmail.com",
    "password": "new_user_password"
}'
```

Afterwhich, use the `/login` endpoint to retrieve the JWT token for the user account. Note that the param name for email here is substituted by `username`

```shell
curl -i --location --request POST 'http://localhost:8080/login' --header 'Content-Type: application/json' --data-raw '{
    "username":"new_user@gmail.com",
    "password": "new_user_password"
}'
```

This should return a bearer token. This bearer token must be used to authorize subsequent API calls. For instance, the following should return a `[200]` response:

```shell
curl -i --location --request GET 'http://localhost:8080/api/v1/account/test' --header 'Content-Type: application/json' --header 'Authorization: Bearer XXX'
```

Alternatively, use `scripts/get_access_token.sh` to get an access token printed in your terminal.
```shell
❯ ./scripts/get_access_token.sh

Bearer XXX
```

## Copyright

Released under the Apache License 2.0. See the [LICENSE](https://github.com/codecentric/springboot-sample-app/blob/master/LICENSE) file.
