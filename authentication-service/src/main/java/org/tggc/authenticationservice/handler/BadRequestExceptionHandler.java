package org.tggc.authenticationservice.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.tggc.authapi.dto.ErrorRs;
import org.tggc.authenticationservice.exception.BadRequestException;

import java.time.LocalDateTime;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RestControllerAdvice
@Slf4j
public class BadRequestExceptionHandler {

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorRs> handleUserAlreadyCreatedException(BadRequestException e) {
        return ResponseEntity.badRequest().body(new ErrorRs(
                BAD_REQUEST,
                e.getMessage(),
                LocalDateTime.now()
        ));
    }

}
