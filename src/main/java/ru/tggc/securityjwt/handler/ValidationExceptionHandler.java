package ru.tggc.securityjwt.handler;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.tggc.securityjwt.dto.ErrorDTO;
import ru.tggc.securityjwt.util.annotations.LoggingError;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static ru.tggc.securityjwt.util.annotations.LoggingErrorConstants.BEFORE;

@RestControllerAdvice
public class ValidationExceptionHandler {

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
