# Java Spring Boot + AWS DynamoDB + Terraform

This example demonstrates a working backend application built in Java Spring Boot 3+
and uses an AWS DynamoDB NOSQL database, created by Terraform.
The focus of this example is integration of DynamoDB.

# Getting Started

1. Create and configure DynamoDB:

```shell
cd ./terraform
terraform init
terraform apply --auto-approve
```

2. Run the backend application.
