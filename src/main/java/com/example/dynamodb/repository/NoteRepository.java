package com.example.dynamodb.repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.example.dynamodb.model.Note;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
public class NoteRepository {

    private DynamoDBMapper dynamoDBMapper;

    public Note create(Note note) {
        dynamoDBMapper.save(note);
        return note;
    }

    public Note findById(String noteId) {
        return dynamoDBMapper.load(
                Note.class,
                noteId
        );
    }

    public List<Note> findAll() {
        return dynamoDBMapper.scan(
                Note.class,
                new DynamoDBScanExpression()
        );
    }

    public Note update(Note note) {
        dynamoDBMapper.save(
                note
        );
        return note;
    }

    public void delete(Note note) {
        dynamoDBMapper.delete(note);
    }

}
