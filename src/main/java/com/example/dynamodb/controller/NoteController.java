package com.example.dynamodb.controller;

import com.example.dynamodb.model.Note;
import com.example.dynamodb.service.NoteService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/api/v1/notes")
public class NoteController {

    private NoteService noteService;

    @PostMapping()
    public ResponseEntity<Note> createNote(
            @RequestBody Note note
    ) {
        Note createdNote = noteService.create(note);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(createdNote);
    }

    @GetMapping("/{noteId}")
    public ResponseEntity<Note> findNoteById(
            @PathVariable("noteId") String noteId
    ) {
        Note note = noteService.findById(noteId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(note);
    }

    @GetMapping()
    public ResponseEntity<List<Note>> findAllNotes() {
        List<Note> notes = noteService.findAll();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(notes);
    }

    @PutMapping("/{noteId}")
    public ResponseEntity<Note> updateById(
            @RequestBody Note note,
            @PathVariable("noteId") String noteId
    ) {
        Note updatedNote = noteService.update(noteId, note);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(updatedNote);
    }

    @DeleteMapping("/{noteId}")
    public ResponseEntity<Void> deleteById(
            @PathVariable("noteId") String noteId
    ) {
        noteService.deleteById(noteId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }

}
