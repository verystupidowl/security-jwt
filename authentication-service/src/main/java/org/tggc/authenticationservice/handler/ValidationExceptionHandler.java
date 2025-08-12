package org.tggc.authenticationservice.handler;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.tggc.authapi.dto.ErrorRs;
import org.tggc.authenticationservice.mapper.ErrorMapper;

@RestControllerAdvice
@RequiredArgsConstructor
public class ValidationExceptionHandler {
    private final ErrorMapper errorMapper;

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public ResponseEntity<ErrorRs> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        return ResponseEntity.badRequest().body(errorMapper.toDto(e));
    }
}
