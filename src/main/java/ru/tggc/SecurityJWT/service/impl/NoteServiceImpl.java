package ru.tggc.SecurityJWT.service.impl;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.tggc.SecurityJWT.exception.NoteNotFoundException;
import ru.tggc.SecurityJWT.model.Note;
import ru.tggc.SecurityJWT.model.User;
import ru.tggc.SecurityJWT.repository.NoteRepository;
import ru.tggc.SecurityJWT.service.NoteService;

import java.util.List;

@Service
public class NoteServiceImpl implements NoteService {

    private final NoteRepository noteRepository;

    @Autowired
    public NoteServiceImpl(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }


    @Override
    public List<Note> findAll() {
        return noteRepository.findAll();
    }

    @Override
    @Transactional
    public void save(Note note, User user) {
        note.setOwner(user);
        noteRepository.save(note);
    }

    @Override
    public List<Note> findByOwner(User user) {
        return noteRepository.findByOwner(user);
    }

    @Override
    public void deleteById(long id) {
        noteRepository.deleteById(id);
    }

    @Override
    public Note findById(long id) {
        return noteRepository.findById(id).orElseThrow(NoteNotFoundException::new);
    }

    @Override
    public List<Note> findByColorAndUser(String color, User user) {
        return noteRepository.findByColorAndOwner(color, user);
    }
}
