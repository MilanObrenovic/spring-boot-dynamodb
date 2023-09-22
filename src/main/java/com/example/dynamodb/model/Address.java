package com.example.dynamodb.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@DynamoDBDocument
@NoArgsConstructor
@AllArgsConstructor
public class Address {

    @DynamoDBAttribute(attributeName = "city")
    @JsonProperty("city")
    private String city;

    @DynamoDBAttribute(attributeName = "country")
    @JsonProperty("country")
    private String country;

}
