provider "aws" {
	region                   = "eu-central-1"
	profile                  = "terraform"
	shared_credentials_files = ["$HOME/.aws/credentials"]
}
