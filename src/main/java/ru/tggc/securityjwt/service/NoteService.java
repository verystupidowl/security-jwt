package ru.tggc.securityjwt.service;

import org.springframework.stereotype.Service;
import ru.tggc.securityjwt.dto.domain.NoteDto;
import ru.tggc.securityjwt.model.User;

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
