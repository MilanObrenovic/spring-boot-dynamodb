// -----------------------------------------------------------------------------------------------------
// @ Auto-Scaling
// -----------------------------------------------------------------------------------------------------
// - For tables on the PROVISIONED billing mode, you can configure auto-scaling so that DynamoDB scales
// 	 up to adjust the provisioned capacity based on traffic patterns.
// - Autoscaling module used:
// 	 https://registry.terraform.io/modules/snowplow-devops/dynamodb-autoscaling/aws/latest.
// - When adding this module for your table, Terraform automatically configures the autoscaling:
// 	 - policies: read, write
// 	 - targets: read, write
// - Additionally, it provides a default configuration for read/write max capacity along with the
// 	 read/write cool in/out scale that you can customize.
// -----------------------------------------------------------------------------------------------------

module "dynamodb-autoscaling" {
	source     = "snowplow-devops/dynamodb-autoscaling/aws"
	version    = "0.2.1"
	table_name = aws_dynamodb_table.note.name
}
