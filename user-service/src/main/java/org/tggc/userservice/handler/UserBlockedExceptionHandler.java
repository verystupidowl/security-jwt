package org.tggc.userservice.handler;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.tggc.userservice.dto.ErrorRs;
import org.tggc.userservice.exception.UserBlockedException;

import java.time.LocalDateTime;

import static org.springframework.http.HttpStatus.FORBIDDEN;

@RestControllerAdvice
public class UserBlockedExceptionHandler {

    @ExceptionHandler(UserBlockedException.class)
    public ResponseEntity<ErrorRs> handleUserBlockedException(UserBlockedException ex) {
        var rs = new ErrorRs(
                FORBIDDEN,
                ex.getMessage(),
                LocalDateTime.now()
        );
        return ResponseEntity
                .status(FORBIDDEN)
                .body(rs);
    }
}
