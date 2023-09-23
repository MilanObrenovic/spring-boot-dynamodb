output "access_key" {
	value = aws_iam_access_key.dynamodb_access_key.id
}

output "secret_key" {
	value = aws_iam_access_key.dynamodb_access_key.secret
	sensitive = true
}
