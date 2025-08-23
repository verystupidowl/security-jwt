package org.tggc.userservice.handler;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.tggc.userservice.dto.ErrorRs;
import org.tggc.userservice.exception.UserBlockedException;
import org.tggc.userservice.exception.UserNotFoundException;

import java.time.LocalDateTime;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestControllerAdvice
public class UserNotFoundExceptionHandler {

    @ExceptionHandler(UserBlockedException.class)
    public ResponseEntity<ErrorRs> handleUserNotFoundException(UserNotFoundException ex) {
        var rs = new ErrorRs(
                NOT_FOUND,
                ex.getMessage(),
                LocalDateTime.now()
        );
        return ResponseEntity
                .status(NOT_FOUND)
                .body(rs);
    }
}
