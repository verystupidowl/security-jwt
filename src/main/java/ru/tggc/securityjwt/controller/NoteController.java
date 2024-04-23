package ru.tggc.securityjwt.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.tggc.securityjwt.dto.NoteDTO;
import ru.tggc.securityjwt.model.User;
import ru.tggc.securityjwt.service.NoteService;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/v1/note")
@CrossOrigin
@Slf4j
public class NoteController {

    private final NoteService noteService;

    @Autowired
    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @GetMapping("/")
    public List<NoteDTO> findAllNotes() {
        return noteService.findAll();
    }

    @GetMapping("/myNotes")
    public List<NoteDTO> findNotesByUser(UsernamePasswordAuthenticationToken token) {
        User user = (User) token.getPrincipal();
        return noteService.findByOwner(user);
    }

    @GetMapping("/{id}")
    public NoteDTO findById(@PathVariable long id) {
        return noteService.findById(id);
    }

    @GetMapping("/color/{color}")
    public List<NoteDTO> findByColor(@PathVariable String color, UsernamePasswordAuthenticationToken token) {
        User user = (User) token.getPrincipal();
        return noteService.findByColorAndUser(STR."#\{color}", user);
    }

    @PostMapping
    public ResponseEntity<Boolean> addNote(@Valid @RequestBody NoteDTO note, UsernamePasswordAuthenticationToken token) {
        User user = (User) token.getPrincipal();
        noteService.save(note, user);
        return ResponseEntity.status(CREATED).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteNote(@PathVariable long id) {
        noteService.deleteById(id);
        return ResponseEntity.status(OK).build();
    }

    @PutMapping
    public ResponseEntity<Boolean> editNote(@Valid @RequestBody NoteDTO note, UsernamePasswordAuthenticationToken token) {
        User user = (User) token.getPrincipal();
        noteService.save(note, user);
        return ResponseEntity.status(OK).build();
    }

}
