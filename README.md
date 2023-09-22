# Java Spring Boot + AWS DynamoDB + Terraform

This example demonstrates a working backend application built in Java Spring Boot 3+
and uses an AWS DynamoDB NOSQL database, created by Terraform.
The focus of this example is integration of DynamoDB.

# 1. Getting Started

## 1.1. Create DynamoDB via Terraform

Create and configure DynamoDB:

```shell
cd ./terraform
terraform init
terraform apply --auto-approve
```

## 1.2. Run the backend application

There are several ways to run it.

### 1.2.1. Manual

Open the project manually via IntelliJ and run it.

### 1.2.2. Docker

First of all, generate a JAR file from the current backend:

```shell
./gradlew clean build
```

To utilize the Dockerfile, build an image of this backend:

```shell
docker build -t spring-boot-dynamodb-demo .
```

Verify the image has been created:

```shell
docker image ls
```

Run the Docker Container from that image:

```shell
docker run --rm -d -p 8081:8081 --name spring-boot-dynamodb spring-boot-dynamodb-demo
```

### 1.2.3. JIB

Generate a JAR file of the backend and build a docker image around it using the JIB plugin:

```shell
./gradlew clean build jibDockerBuild
```

Run the container from the generated image:

```shell
docker run --rm -d -p 8081:8081 --name spring-boot-dynamodb spring-boot-dynamodb-demo
```

### 1.2.3. Docker Compose

Run the backend via Docker Compose:

```shell
docker compose -up -d
```
