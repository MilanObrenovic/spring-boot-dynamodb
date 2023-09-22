package com.example.dynamodb.model;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@DynamoDBTable(tableName = "note")
public class Note {

    @DynamoDBHashKey
    @DynamoDBAutoGeneratedKey
    @DynamoDBAttribute(attributeName = "id")
    @JsonProperty("id")
    private String id;

    @DynamoDBAttribute(attributeName = "content")
    @JsonProperty("content")
    private String content;

    @DynamoDBAttribute(attributeName = "address")
    @JsonProperty("address")
    private Address address;

    @DynamoDBAttribute(attributeName = "created_at")
    @DynamoDBAutoGeneratedTimestamp(strategy = DynamoDBAutoGenerateStrategy.CREATE)
    @JsonProperty("created_at")
    private Date createdAt;

    @DynamoDBAttribute(attributeName = "updated_at")
    @DynamoDBAutoGeneratedTimestamp(strategy = DynamoDBAutoGenerateStrategy.ALWAYS)
    @DynamoDBTypeConvertedTimestamp
    @JsonProperty("updated_at")
    private Date updatedAt;

}