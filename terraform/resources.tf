# Create an IAM user called `dynamodb`
resource "aws_iam_user" "dynamodb" {
	name = "dynamodb"
}

# Attach `AdministratorAccess` to the previously created user `dynamodb`
resource "aws_iam_user_policy_attachment" "dynamodb_user_policy_attachment" {
	user       = aws_iam_user.dynamodb.name
	policy_arn = "arn:aws:iam::aws:policy/AdministratorAccess"
}

# Create a new user group called `dynamodb_access_group`
resource "aws_iam_group" "dynamodb_access_group" {
	name = "dynamodb_access_group"
}

# Add the previously defined user `dynamodb` to the previously created user group `dynamodb_access_group`
resource "aws_iam_group_membership" "dynamodb_access_group_users" {
	group = aws_iam_group.dynamodb_access_group.name
	name  = "dynamodb_access_group_users"
	users = [
		aws_iam_user.dynamodb.name,
	]
}

# Attach `AmazonDynamoDBFullAccess` to the previously created user group `dynamodb_access_group`
resource "aws_iam_group_policy_attachment" "dynamodb_access_group_policy_attachment" {
	group      = aws_iam_group.dynamodb_access_group.name
	policy_arn = "arn:aws:iam::aws:policy/AmazonDynamoDBFullAccess"
}

# Output access and secret keys of the user `dynamodb`
resource "aws_iam_access_key" "dynamodb_access_key" {
	user = aws_iam_user.dynamodb.name
}

# Save the credentials in `~/.aws/credentials` under `dynamodb` profile
resource "null_resource" "configure_aws_cli" {
	depends_on = [
		aws_iam_user.dynamodb,
	]

	provisioner "local-exec" {
		command = <<-EOT
			ACCESS_KEY=$(terraform output --raw access_key)
			aws configure set aws_access_key_id $ACCESS_KEY --profile dynamodb
			SECRET_KEY=$(terraform output --raw secret_key)
			aws configure set aws_secret_access_key "$SECRET_KEY" --profile dynamodb
		EOT
	}
}

resource "null_resource" "configure_vault" {
	depends_on = [
		null_resource.configure_aws_cli,
	]

	provisioner "local-exec" {
		command = <<-EOT
			#!/bin/bash

			VAULT_TOKEN="spring-boot-dynamodb-token"

			# Start Vault server
			vault server -dev --dev-root-token-id="$VAULT_TOKEN" &

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
		EOT
	}
}

# Create a new DynamoDB table called `note`
resource "aws_dynamodb_table" "note" {
	name           = "note"
	billing_mode   = "PROVISIONED"
	read_capacity  = 30
	write_capacity = 30
	hash_key       = "id"
	depends_on     = [
		aws_iam_user.dynamodb,
		null_resource.configure_aws_cli,
	]

	# Tell DynamoDB that the primary key `id` is of type String
	attribute {
		name = "id"
		type = "S"
	}

	# Enable Time-To-Live (TTL) for automatic item expiration
	ttl {
		enabled        = true
		attribute_name = "expiry_period"
	}

	# Enable Point-In-Time Recovery (PITR) for the table in case of a disaster
	point_in_time_recovery {
		enabled = true
	}

	# Enable server-side encryption for data at REST
	server_side_encryption {
		enabled = true
	}

	# Configure the "lifecycle" block to ignore changes to read and write capacity
	lifecycle {
		ignore_changes = [
			write_capacity,
			read_capacity,
		]
	}
}
