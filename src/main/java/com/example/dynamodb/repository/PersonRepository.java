package com.example.dynamodb.repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.example.dynamodb.model.Person;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class PersonRepository {

    private DynamoDBMapper dynamoDBMapper;

    public Person create(Person person) {
        dynamoDBMapper.save(person);
        return person;
    }

}
