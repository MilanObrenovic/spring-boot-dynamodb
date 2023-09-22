package com.example.dynamodb.serviceImpl;

import com.example.dynamodb.model.Note;
import com.example.dynamodb.repository.NoteRepository;
import com.example.dynamodb.service.NoteService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class NoteServiceImpl implements NoteService {

    private NoteRepository noteRepository;

    @Override
    public Note create(Note note) {
        return noteRepository.create(note);
    }

    @Override
    public Note findById(String noteId) {
        return noteRepository.findById(noteId);
    }

    @Override
    public List<Note> findAll() {
        return noteRepository.findAll();
    }

    @Override
    public Note update(String noteId, Note updatedNote) {
        Note existingNote = findById(noteId);

        boolean changes = false;

        if (existingNote == null) {
            return null;
        }

        // If content was changed, update it
        if (canUpdate(existingNote.getContent(), updatedNote.getContent())) {
            existingNote.setContent(updatedNote.getContent());
            changes = true;
            log.info("UPDATED: content");
        }

        // If the address was changed...
        if (updatedNote.getAddress() != null) {

            // ...and if city was changed, update it.
            if (canUpdate(existingNote.getAddress().getCity(), updatedNote.getAddress().getCity())) {
                existingNote.getAddress().setCity(updatedNote.getAddress().getCity());
                changes = true;
                log.info("UPDATED: city");
            }

            // ...and if country was changed, update it.
            if (canUpdate(existingNote.getAddress().getCountry(), updatedNote.getAddress().getCountry())) {
                existingNote.getAddress().setCountry(updatedNote.getAddress().getCountry());
                changes = true;
                log.info("UPDATED: country");
            }
        }

        log.info("Changes: {}", changes);

        if (!changes) {
            return null;
        }

        return noteRepository.update(existingNote);
    }

    @Override
    public void deleteById(String noteId) {
        Note note = findById(noteId);
        if (note != null) {
            noteRepository.delete(note);
        }
    }

    private boolean canUpdate(Object existingObject, Object newObject) {
        // If the object is null, ignore it
        if (newObject == null) {
            return false;
        }

        // If the object IS empty, ignore it
        if (newObject instanceof String) {
            if (newObject.toString().isEmpty() || newObject.toString().isBlank()) {
                return false;
            }
        }

        // If the object is the same as the existing one, ignore it
        if (newObject.equals(existingObject)) {
            return false;
        }

        // This field can be updated now
        return true;
    }
}
