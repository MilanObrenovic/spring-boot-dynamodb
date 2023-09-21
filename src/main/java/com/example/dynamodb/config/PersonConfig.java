package com.example.dynamodb.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PersonConfig {

    @Value("${dynamodb.accessKey}")
    private String iamDynamodbAccessKey;

    @Value("${dynamodb.secretKey}")
    private String iamDynamodbSecretKey;

    @Bean
    public DynamoDBMapper dynamoDBMapper() {
        return new DynamoDBMapper(buildAmazonDynamoDB());
    }

    private AmazonDynamoDB buildAmazonDynamoDB() {
        AwsClientBuilder.EndpointConfiguration endpointConfiguration = new AwsClientBuilder.EndpointConfiguration(
                "dynamodb.eu-central-1.amazonaws.com",
                "eu-central-1"
        );

        BasicAWSCredentials basicAWSCredentials = new BasicAWSCredentials(
                iamDynamodbAccessKey,
                iamDynamodbSecretKey
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
