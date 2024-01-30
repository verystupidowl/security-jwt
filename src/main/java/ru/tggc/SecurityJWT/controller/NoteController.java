package ru.tggc.SecurityJWT.controller;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import ru.tggc.SecurityJWT.dto.ErrorDTO;
import ru.tggc.SecurityJWT.exception.NoteNotFoundException;
import ru.tggc.SecurityJWT.model.Note;
import ru.tggc.SecurityJWT.model.User;
import ru.tggc.SecurityJWT.service.NoteService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/note")
@CrossOrigin
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
        return noteService.findByColorAndUser("#" + color, user);
    }

    @PostMapping
    public ResponseEntity<Boolean> addNote(@Valid @RequestBody Note note, UsernamePasswordAuthenticationToken token) {
        User user = (User) token.getPrincipal();
        noteService.save(note, user);
        return ResponseEntity.ok(true);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteNote(@PathVariable long id) {
        noteService.deleteById(id);
        return ResponseEntity.ok(true);
    }

    @PutMapping("/edit")
    public ResponseEntity<Boolean> editNote(@Valid @RequestBody Note note, UsernamePasswordAuthenticationToken token) {
        User user = (User) token.getPrincipal();
        noteService.editNote(note, user);
        return ResponseEntity.ok(true);
    }

    @ExceptionHandler(value = {NoteNotFoundException.class})
    public ResponseEntity<ErrorDTO> handleNoteNotFoundException(NoteNotFoundException e) {
        return ResponseEntity.badRequest().body(new ErrorDTO(e.getMessage(), LocalDateTime.now()));
    }

    @ExceptionHandler(value = {ConstraintViolationException.class})
    public ResponseEntity<ErrorDTO> handleConstraintViolationException(ConstraintViolationException e) {
        return ResponseEntity.badRequest().body(new ErrorDTO(
                e.getConstraintViolations()
                        .stream()
                        .map(ConstraintViolation::getMessageTemplate)
                        .collect(Collectors.joining(", ")),
                LocalDateTime.now()
        ));
    }
}
