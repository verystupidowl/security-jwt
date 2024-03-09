package ru.tggc.SecurityJWT.service;

import org.springframework.stereotype.Service;
import ru.tggc.SecurityJWT.model.Note;
import ru.tggc.SecurityJWT.model.User;

import java.util.List;

@Service
public interface NoteService {

    List<Note> findAll();

    void save(Note note, User user);

    List<Note> findByOwner(User user);

    void deleteById(long id);

    Note findById(long id);

    List<Note> findByColorAndUser(String color, User user);
}
