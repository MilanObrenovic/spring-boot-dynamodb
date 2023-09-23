#!/bin/bash

# The type of vault we want to create is a secret
VAULT_PATH="secret/dynamodb"

# Target AWS DynamoDB profile from credentials
AWS_PROFILE="dynamodb"

# Read AWS IAM credentials from ~/.aws/credentials
AWS_ACCESS_KEY_ID=$(aws configure get aws_access_key_id --profile "$AWS_PROFILE")
AWS_SECRET_ACCESS_KEY=$(aws configure get aws_secret_access_key --profile "$AWS_PROFILE")

# To be able to write credentials to Vault
export VAULT_ADDR="http://127.0.0.1:8200"
export VAULT_TOKEN="spring-boot-dynamodb-token"

# Write the credentials to Vault
vault kv put "$VAULT_PATH" aws.iam.dynamodb.accessKey="$AWS_ACCESS_KEY_ID" aws.iam.dynamodb.secretKey="$AWS_SECRET_ACCESS_KEY"
