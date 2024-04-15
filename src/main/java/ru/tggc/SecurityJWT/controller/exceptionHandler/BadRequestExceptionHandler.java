package ru.tggc.SecurityJWT.controller.exceptionHandler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.tggc.SecurityJWT.dto.ErrorDTO;
import ru.tggc.SecurityJWT.exception.UserAlreadyCreatedException;
import ru.tggc.SecurityJWT.util.annotations.LoggingError;

import java.time.LocalDateTime;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static ru.tggc.SecurityJWT.util.annotations.LoggingErrorConstants.BEFORE;

@RestControllerAdvice
@Slf4j
public class BadRequestExceptionHandler {

    @ExceptionHandler(value = {UserAlreadyCreatedException.class})
    @LoggingError(time = BEFORE)
    public ResponseEntity<ErrorDTO> handleUserAlreadyCreatedException(UserAlreadyCreatedException e) {
        return ResponseEntity.badRequest().body(new ErrorDTO(
                BAD_REQUEST, e.getMessage(), LocalDateTime.now()
        ));
    }

}
