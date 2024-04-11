package ru.tggc.SecurityJWT.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.tggc.SecurityJWT.dto.ErrorDTO;
import ru.tggc.SecurityJWT.exception.NoteNotFoundException;
import ru.tggc.SecurityJWT.exception.UserAlreadyCreatedException;
import ru.tggc.SecurityJWT.util.annotations.LoggingError;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static ru.tggc.SecurityJWT.util.annotations.LoggingErrorConstants.BEFORE;

@RestControllerAdvice
@Slf4j
public class ExceptionHandlerController {

    @ExceptionHandler(value = {UserAlreadyCreatedException.class, NoteNotFoundException.class})
    @LoggingError(time = BEFORE)
    public ResponseEntity<ErrorDTO> handleUserAlreadyCreatedException(Exception e) {
        ErrorDTO errorDTO = new ErrorDTO(BAD_REQUEST, e.getMessage(), LocalDateTime.now());
        return ResponseEntity.badRequest().body(errorDTO);
    }


    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    @LoggingError(time = BEFORE)
    public ResponseEntity<ErrorDTO> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        ErrorDTO errorDTO = new ErrorDTO(
                BAD_REQUEST,
                e.getFieldErrors()
                        .stream()
                        .map(FieldError::getDefaultMessage)
                        .collect(Collectors.joining(", ")),
                LocalDateTime.now()
        );
        return ResponseEntity.badRequest().body(errorDTO);
    }
}
