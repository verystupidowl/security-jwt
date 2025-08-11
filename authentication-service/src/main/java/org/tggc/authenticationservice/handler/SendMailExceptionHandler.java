package org.tggc.authenticationservice.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.tggc.authapi.dto.ErrorRs;
import org.tggc.authenticationservice.exception.SendMailException;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@ControllerAdvice
@Slf4j
public class SendMailExceptionHandler {

    @ExceptionHandler(SendMailException.class)
    public Mono<ResponseEntity<ErrorRs>> sendMailExceptionHandler(SendMailException e) {
        return Mono.just(ResponseEntity.internalServerError().body(new ErrorRs(
                HttpStatus.INTERNAL_SERVER_ERROR,
                e.getMessage(),
                LocalDateTime.now()
        )));
    }
}
