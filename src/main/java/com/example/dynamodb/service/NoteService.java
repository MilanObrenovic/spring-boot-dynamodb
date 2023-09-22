package com.example.dynamodb.service;

import com.example.dynamodb.model.Note;

import java.util.List;

public interface NoteService {

    Note create(Note note);
    Note findById(String noteId);
    List<Note> findAll();
    Note update(String noteId, Note updatedNote);
    void deleteById(String noteId);

}
