package org.tggc.authenticationservice.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.tggc.authapi.dto.ErrorRs;
import org.tggc.authenticationservice.exception.UsernameNotFoundException;

import java.time.LocalDateTime;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@Slf4j
@RestControllerAdvice
public class UnauthorizedExceptionHandler {

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ErrorRs> handleUsernameNotFoundException(UsernameNotFoundException e) {
        log.info("catch {}", e.getMessage());
        return new ResponseEntity<>(new ErrorRs(UNAUTHORIZED, e.getMessage(), LocalDateTime.now()), UNAUTHORIZED);
    }
}
