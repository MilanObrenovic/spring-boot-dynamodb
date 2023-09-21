package com.example.dynamodb.controller;

import com.example.dynamodb.model.Person;
import com.example.dynamodb.repository.PersonRepository;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/api/v1/persons")
public class PersonController {

    private PersonRepository personRepository;

    @PostMapping()
    public Person createPerson(
            @RequestBody Person person
    ) {
        return personRepository.create(person);
    }

}
