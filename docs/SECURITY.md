# Security boundaries

Use synthetic data. Never commit credentials, customer records, billing exports or Terraform state. Validate identity before constructing tenant context. Do not trust a tenant ID from a request body. Rotate credentials and verify provider webhooks before handling them. Production integrations require a threat model and least-privilege credentials.
