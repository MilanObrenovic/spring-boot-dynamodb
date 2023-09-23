package com.example.dynamodb.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@AllArgsConstructor
public class DynamoDBConfig {

    @Value("${dynamodb.endpoint}")
    private String dynamodbEndpoint;

    @Value("${dynamodb.region}")
    private String dynamodbRegion;

    private VaultConfig vaultConfig;

    @Autowired
    public DynamoDBConfig(VaultConfig vaultConfig) {
        this.vaultConfig = vaultConfig;
    }

    @Bean
    public DynamoDBMapper dynamoDBMapper() {
        return new DynamoDBMapper(buildAmazonDynamoDB());
    }

    private AmazonDynamoDB buildAmazonDynamoDB() {
        AwsClientBuilder.EndpointConfiguration endpointConfiguration = new AwsClientBuilder.EndpointConfiguration(
                dynamodbEndpoint,
                dynamodbRegion
        );

        BasicAWSCredentials basicAWSCredentials = new BasicAWSCredentials(
                vaultConfig.getAccessKey(),
                vaultConfig.getSecretKey()
        );

        AWSStaticCredentialsProvider awsStaticCredentialsProvider = new AWSStaticCredentialsProvider(
                basicAWSCredentials
        );

        return AmazonDynamoDBClientBuilder
                .standard()
                .withEndpointConfiguration(endpointConfiguration)
                .withCredentials(awsStaticCredentialsProvider)
                .build();
    }

}
