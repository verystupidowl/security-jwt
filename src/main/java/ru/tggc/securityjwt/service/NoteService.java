package ru.tggc.securityjwt.service;

import org.springframework.stereotype.Service;
import ru.tggc.securityjwt.dto.NoteDTO;
import ru.tggc.securityjwt.model.User;

import java.util.List;

@Service
public interface NoteService {

    List<NoteDTO> findAll();

    NoteDTO save(NoteDTO note, User user);

    List<NoteDTO> findByOwner(User user);

    void deleteById(long id);

    NoteDTO findById(long id);

    List<NoteDTO> findByColorAndUser(String color, User user);
}
