provider "aws" {
	region                   = "eu-central-1"
	profile                  = "dynamodb"
	shared_credentials_files = ["$HOME/.aws/credentials"]
}
