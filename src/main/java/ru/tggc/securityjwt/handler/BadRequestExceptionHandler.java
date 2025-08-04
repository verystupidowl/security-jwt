package ru.tggc.securityjwt.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.tggc.securityjwt.dto.ErrorDto;
import ru.tggc.securityjwt.exception.BadRequestException;
import ru.tggc.securityjwt.util.annotations.LoggingError;

import java.time.LocalDateTime;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static ru.tggc.securityjwt.util.annotations.LoggingErrorConstants.BEFORE;

@RestControllerAdvice
@Slf4j
public class BadRequestExceptionHandler {

    @ExceptionHandler(BadRequestException.class)
    @LoggingError(time = BEFORE)
    public ResponseEntity<ErrorDto> handleUserAlreadyCreatedException(BadRequestException e) {
        return ResponseEntity.badRequest().body(new ErrorDto(
                BAD_REQUEST,
                e.getMessage(),
                LocalDateTime.now()
        ));
    }

}
