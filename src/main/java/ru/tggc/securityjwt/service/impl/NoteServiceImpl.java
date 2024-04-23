package ru.tggc.securityjwt.service.impl;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.tggc.securityjwt.dto.NoteDTO;
import ru.tggc.securityjwt.exception.NoteNotFoundException;
import ru.tggc.securityjwt.model.Note;
import ru.tggc.securityjwt.model.User;
import ru.tggc.securityjwt.repository.NoteRepository;
import ru.tggc.securityjwt.service.NoteService;
import ru.tggc.securityjwt.util.annotations.Profiling;
import ru.tggc.securityjwt.util.mapper.NoteMapper;

import java.util.List;

@Service
@Profiling
public class NoteServiceImpl implements NoteService {

    private final NoteRepository noteRepository;

    private final NoteMapper mapper;

    @Autowired
    public NoteServiceImpl(NoteRepository noteRepository, NoteMapper noteMapper) {
        this.noteRepository = noteRepository;
        this.mapper = noteMapper;
    }


    @Override
    public List<NoteDTO> findAll() {
        return noteRepository.findAll().stream()
                .map(mapper::toDto)
                .toList();
    }

    @Override
    @Transactional
    public NoteDTO save(NoteDTO dto, User user) {
        Note note = mapper.toEntity(dto);
        note.setOwner(user);
        return mapper.toDto(noteRepository.save(note));
    }

    @Override
    public List<NoteDTO> findByOwner(User user) {
        return noteRepository.findByOwner(user).stream()
                .map(mapper::toDto)
                .toList();
    }

    @Override
    public void deleteById(long id) {
        noteRepository.deleteById(id);
    }

    @Override
    public NoteDTO findById(long id) {
        return noteRepository.findById(id)
                .map(mapper::toDto)
                .orElseThrow(() -> new NoteNotFoundException(STR."Not found note with id \{id}"));
    }

    @Override
    public List<NoteDTO> findByColorAndUser(String color, User user) {
        return noteRepository.findByColorAndOwner(color, user).stream()
                .map(mapper::toDto)
                .toList();
    }
}
