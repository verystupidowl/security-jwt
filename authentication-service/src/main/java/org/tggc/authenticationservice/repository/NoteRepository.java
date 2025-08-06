package org.tggc.authenticationservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.tggc.authenticationservice.model.Note;
import org.tggc.authenticationservice.model.User;

import java.util.List;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {

    List<Note> findByOwner(User owner);

    List<Note> findByColorAndOwner(String color, User user);
}
