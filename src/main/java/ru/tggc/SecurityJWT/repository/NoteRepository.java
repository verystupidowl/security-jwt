package ru.tggc.SecurityJWT.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.tggc.SecurityJWT.model.Note;
import ru.tggc.SecurityJWT.model.User;

import java.util.List;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {

    List<Note> findByOwner(User owner);

    List<Note> findByColorAndOwner(String color, User user);
}
