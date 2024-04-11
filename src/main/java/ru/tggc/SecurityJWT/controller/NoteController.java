package ru.tggc.SecurityJWT.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import ru.tggc.SecurityJWT.model.Note;
import ru.tggc.SecurityJWT.model.User;
import ru.tggc.SecurityJWT.service.NoteService;

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
    public List<Note> findAllNotes() {
        return noteService.findAll();
    }

    @GetMapping("/myNotes")
    public List<Note> findNotesByUser(UsernamePasswordAuthenticationToken token) {
        User user = (User) token.getPrincipal();
        return noteService.findByOwner(user);
    }

    @GetMapping("/{id}")
    public Note findById(@PathVariable long id) {
        return noteService.findById(id);
    }

    @GetMapping("/color/{color}")
    public List<Note> findByColor(@PathVariable String color, UsernamePasswordAuthenticationToken token) {
        User user = (User) token.getPrincipal();
        return noteService.findByColorAndUser(STR."#\{color}", user);
    }

    @PostMapping
    public ResponseEntity<Boolean> addNote(@Valid @RequestBody Note note, UsernamePasswordAuthenticationToken token) {
        User user = (User) token.getPrincipal();
        noteService.save(note, user);
        return ResponseEntity.status(CREATED).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteNote(@PathVariable long id) {
        noteService.deleteById(id);
        return ResponseEntity.status(OK).build();
    }

    @PutMapping("/edit")
    public ResponseEntity<Boolean> editNote(@Valid @RequestBody Note note, UsernamePasswordAuthenticationToken token) {
        User user = (User) token.getPrincipal();
        noteService.save(note, user);
        return ResponseEntity.status(OK).build();
    }

}
