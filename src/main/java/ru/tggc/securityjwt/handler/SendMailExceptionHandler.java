package ru.tggc.securityjwt.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.tggc.securityjwt.dto.ErrorDto;
import ru.tggc.securityjwt.exception.SendMailException;

import java.time.LocalDateTime;

@ControllerAdvice
@Slf4j
public class SendMailExceptionHandler {

    @ExceptionHandler(SendMailException.class)
    public ResponseEntity<ErrorDto> sendMailExceptionHandler(SendMailException e) {
        return ResponseEntity.internalServerError().body(new ErrorDto(
                HttpStatus.INTERNAL_SERVER_ERROR,
                e.getMessage(),
                LocalDateTime.now()
        ));
    }
}
