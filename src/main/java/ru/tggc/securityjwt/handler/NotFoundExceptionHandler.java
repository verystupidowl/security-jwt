package ru.tggc.securityjwt.handler;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.tggc.securityjwt.dto.response.ErrorRs;
import ru.tggc.securityjwt.exception.NoteNotFoundException;

import java.time.LocalDateTime;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestControllerAdvice
public class NotFoundExceptionHandler {

    @ExceptionHandler(value = {NoteNotFoundException.class})
    public ResponseEntity<ErrorRs> handleNoteNotFoundException(NoteNotFoundException e) {
        return ResponseEntity.status(NOT_FOUND).body(new ErrorRs(
                BAD_REQUEST,
                e.getMessage(),
                LocalDateTime.now()
        ));
    }
}
