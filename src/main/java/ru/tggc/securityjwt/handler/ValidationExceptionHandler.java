package ru.tggc.securityjwt.handler;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.tggc.securityjwt.dto.ErrorDto;
import ru.tggc.securityjwt.mapper.ErrorMapper;

@RestControllerAdvice
@RequiredArgsConstructor
public class ValidationExceptionHandler {
    private final ErrorMapper errorMapper;

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public ResponseEntity<ErrorDto> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        return ResponseEntity.badRequest().body(errorMapper.toDto(e));
    }
}
