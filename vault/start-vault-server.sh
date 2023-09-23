#!/bin/bash

VAULT_TOKEN="spring-boot-dynamodb-token"

# Start Vault server
vault server -dev --dev-root-token-id="$VAULT_TOKEN" &
