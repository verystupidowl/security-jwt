package org.tggc.authenticationservice.service;

import org.springframework.stereotype.Service;
import org.tggc.authenticationservice.dto.domain.NoteDto;

import java.util.List;

@Service
public interface NoteService {

    List<NoteDto> findAll();

    NoteDto save(NoteDto note, String email);

    List<NoteDto> findByOwner(String id);

    void deleteById(long id);

    NoteDto findById(long id);

    List<NoteDto> findByColorAndUser(String color, String email);
}
