# Backend

[![Build Status](https://travis-ci.org/codecentric/springboot-sample-app.svg?branch=master)](https://travis-ci.org/codecentric/springboot-sample-app)
[![Coverage Status](https://coveralls.io/repos/github/codecentric/springboot-sample-app/badge.svg?branch=master)](https://coveralls.io/github/codecentric/springboot-sample-app?branch=master)
[![License](http://img.shields.io/:license-apache-blue.svg)](http://www.apache.org/licenses/LICENSE-2.0.html)

Minimal [Spring Boot](http://projects.spring.io/spring-boot/) sample app.

## Requirements

For building and running the application you need:

- [JDK 1.8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
- [Maven 3](https://maven.apache.org)

## Running the application locally

There are several ways to run a Spring Boot application on your local machine. One way is to execute the `main` method in the `cpa.src.main.java.com.is442project.cpa.CorporatePassApplication` class from your IDE.

Alternatively you can use the [Spring Boot Maven plugin](https://docs.spring.io/spring-boot/docs/current/reference/html/build-tool-plugins-maven-plugin.html).

Navigate to `.../group-project-g1t1-backend/cpa` and execute the following command:

```shell
mvn spring-boot:run
```

By default, the application runs on port 8080.

## Authentication via JWT

All endpoints (except for `/api/v1/admin/create`) require authentication via JWT. This token is issued by the `/login` endpoint.

In order to request a token, a user account must first be created.

```shell
curl --location --request POST 'http://localhost:8080/api/v1/admin/create' \
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
curl -i --location --request GET 'http://localhost:8080/api/v1/admin/test' --header 'Content-Type: application/json' --header 'Authorization: Bearer XXX'
```

Alternatively, use `scripts/get_access_token.sh` to get an access token printed in your terminal.
```shell
❯ ./scripts/get_access_token.sh

Bearer XXX
```

## Deploying the application to OpenShift

The easiest way to deploy the sample application to OpenShift is to use the [OpenShift CLI](https://docs.openshift.org/latest/cli_reference/index.html):

```shell
oc new-app codecentric/springboot-maven3-centos~https://github.com/codecentric/springboot-sample-app
```

This will create:

* An ImageStream called "springboot-maven3-centos"
* An ImageStream called "springboot-sample-app"
* A BuildConfig called "springboot-sample-app"
* DeploymentConfig called "springboot-sample-app"
* Service called "springboot-sample-app"

If you want to access the app from outside your OpenShift installation, you have to expose the springboot-sample-app service:

```shell
oc expose springboot-sample-app --hostname=www.example.com
```

## Copyright

Released under the Apache License 2.0. See the [LICENSE](https://github.com/codecentric/springboot-sample-app/blob/master/LICENSE) file.
