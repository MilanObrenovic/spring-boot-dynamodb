# https://registry.terraform.io/modules/snowplow-devops/dynamodb-autoscaling/aws/latest
module "dynamodb-autoscaling" {
	source     = "snowplow-devops/dynamodb-autoscaling/aws"
	version    = "0.2.1"
	table_name = aws_dynamodb_table.note.name
}
