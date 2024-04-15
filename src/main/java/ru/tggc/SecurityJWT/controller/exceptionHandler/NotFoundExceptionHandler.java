package ru.tggc.SecurityJWT.controller.exceptionHandler;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.tggc.SecurityJWT.dto.ErrorDTO;
import ru.tggc.SecurityJWT.exception.NoteNotFoundException;

import java.time.LocalDateTime;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestControllerAdvice
public class NotFoundExceptionHandler {

    @ExceptionHandler(value = {NoteNotFoundException.class})
    public ResponseEntity<ErrorDTO> handleNoteNotFoundException(NoteNotFoundException e) {
        return ResponseEntity.status(NOT_FOUND).body(new ErrorDTO(
                BAD_REQUEST,
                e.getMessage(),
                LocalDateTime.now()
        ));
    }
}
