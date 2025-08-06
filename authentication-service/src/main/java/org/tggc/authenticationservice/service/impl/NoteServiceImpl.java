package org.tggc.authenticationservice.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.tggc.authenticationservice.dto.domain.NoteDto;
import org.tggc.authenticationservice.exception.NoteNotFoundException;
import org.tggc.authenticationservice.mapper.NoteMapper;
import org.tggc.authenticationservice.model.Note;
import org.tggc.authenticationservice.model.User;
import org.tggc.authenticationservice.repository.NoteRepository;
import org.tggc.authenticationservice.service.NoteService;

import java.util.List;

@Service
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
