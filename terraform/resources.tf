# https://registry.terraform.io/providers/hashicorp/aws/latest/docs/resources/dynamodb_table
resource "aws_dynamodb_table" "notes" {
	name           = "notes"
	billing_mode   = "PROVISIONED"
	read_capacity  = 30
	write_capacity = 30
	hash_key       = "note_id"

	attribute {
		name = "note_id"
		type = "S"
	}

	// -----------------------------------------------------------------------------------------------------
	// @ TTL (Time To Live)
	// -----------------------------------------------------------------------------------------------------
	// - TTL attribute configures an expiry time for data items.
	// - The attribute name which enforces TTL, must be a Number (Timestamp).
	// - After a specified period exceeds, DynamoDB deletes the item.
	// -----------------------------------------------------------------------------------------------------

	ttl {
		enabled        = true
		attribute_name = "expiry_period"
	}

	// -----------------------------------------------------------------------------------------------------
	// @ PITR (Point In Time Recovery)
	// -----------------------------------------------------------------------------------------------------
	// - To ensure your data can get restored in case of a disaster, you can enable Point-in-Time Recovery.
	// - These are continuous backups created by DynamoDB for your table (when enabled).
	// -----------------------------------------------------------------------------------------------------

	point_in_time_recovery {
		enabled = true
	}

	// -----------------------------------------------------------------------------------------------------
	// @ Encryption
	// -----------------------------------------------------------------------------------------------------
	// - Out of the box, DynamoDB encrypts your data as rest.
	// - Terraform allows you to configure the KMS key used for encryption.
	// - This is configured using the block below.
	// - When false -> use AWS Owned CMK.
	// - When true -> use AWS Managed CMK.
	// - When true + kms_key_arn -> use custom key.
	// -----------------------------------------------------------------------------------------------------

	server_side_encryption {
		enabled = true
	}

	// -----------------------------------------------------------------------------------------------------
	// @ Lifecycle
	// -----------------------------------------------------------------------------------------------------
	// - Recommended by Terraform to ignore changes for read/write capacity if autoscaling policy is
	// 	 attached to the table.
	// - This will prevent Terraform from removing the autoscaling actions.
	// -----------------------------------------------------------------------------------------------------

	lifecycle {
		ignore_changes = [
			write_capacity,
			read_capacity,
		]
	}
}
