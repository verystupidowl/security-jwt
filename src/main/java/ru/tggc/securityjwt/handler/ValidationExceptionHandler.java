package ru.tggc.securityjwt.handler;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.tggc.securityjwt.dto.ErrorDto;
import ru.tggc.securityjwt.mapper.ErrorMapper;
import ru.tggc.securityjwt.util.annotations.LoggingError;

import static ru.tggc.securityjwt.util.annotations.LoggingErrorConstants.BEFORE;

@RestControllerAdvice
@RequiredArgsConstructor
public class ValidationExceptionHandler {
    private final ErrorMapper errorMapper;

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    @LoggingError(time = BEFORE)
    public ResponseEntity<ErrorDto> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        return ResponseEntity.badRequest().body(errorMapper.toDto(e));
    }
}
