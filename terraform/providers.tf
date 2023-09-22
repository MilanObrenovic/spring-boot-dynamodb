provider "aws" {
	region                   = "eu-central-1"
	shared_credentials_files = ["$HOME/.aws/credentials_dynamodb"]
}
