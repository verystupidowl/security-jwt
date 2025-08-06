package org.tggc.authenticationservice.service;

import org.springframework.stereotype.Service;
import org.tggc.authenticationservice.dto.domain.NoteDto;
import org.tggc.authenticationservice.model.User;

import java.util.List;

@Service
public interface NoteService {

    List<NoteDto> findAll();

    NoteDto save(NoteDto note, User user);

    List<NoteDto> findByOwner(User user);

    void deleteById(long id);

    NoteDto findById(long id);

    List<NoteDto> findByColorAndUser(String color, User user);
}
