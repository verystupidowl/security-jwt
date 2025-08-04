package ru.tggc.securityjwt.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tggc.securityjwt.dto.NoteDto;
import ru.tggc.securityjwt.exception.NoteNotFoundException;
import ru.tggc.securityjwt.mapper.NoteMapper;
import ru.tggc.securityjwt.model.Note;
import ru.tggc.securityjwt.model.User;
import ru.tggc.securityjwt.repository.NoteRepository;
import ru.tggc.securityjwt.service.NoteService;
import ru.tggc.securityjwt.util.annotations.Profiling;

import java.util.List;

@Service
@Profiling
@RequiredArgsConstructor
public class NoteServiceImpl implements NoteService {
    private final NoteRepository noteRepository;
    private final NoteMapper mapper;

    @Override
    public List<NoteDto> findAll() {
        return noteRepository.findAll().stream()
                .map(mapper::toDto)
                .toList();
    }

    @Override
    @Transactional
    public NoteDto save(NoteDto dto, User user) {
        Note note = mapper.toEntity(dto);
        note.setOwner(user);
        return mapper.toDto(noteRepository.save(note));
    }

    @Override
    public List<NoteDto> findByOwner(User user) {
        return noteRepository.findByOwner(user).stream()
                .map(mapper::toDto)
                .toList();
    }

    @Override
    public void deleteById(long id) {
        noteRepository.deleteById(id);
    }

    @Override
    public NoteDto findById(long id) {
        return noteRepository.findById(id)
                .map(mapper::toDto)
                .orElseThrow(() -> new NoteNotFoundException(id));
    }

    @Override
    public List<NoteDto> findByColorAndUser(String color, User user) {
        return noteRepository.findByColorAndOwner(color, user).stream()
                .map(mapper::toDto)
                .toList();
    }
}
