package org.tggc.authenticationservice.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.tggc.authapi.dto.ErrorRs;
import org.tggc.authenticationservice.exception.GlobalException;
import org.tggc.authenticationservice.mapper.ErrorMapper;

import java.time.LocalDateTime;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@RestControllerAdvice
@Slf4j
@RequiredArgsConstructor
public class GlobalExceptionHandler {
    private final ErrorMapper errorMapper;

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorRs> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        var rs = errorMapper.toDto(e);
        return ResponseEntity.badRequest().body(rs);
    }

    @ExceptionHandler(GlobalException.class)
    public ResponseEntity<ErrorRs> handleException(GlobalException ex) {
        var body = new ErrorRs(
                ex.getHttpStatus(),
                ex.getMessage(),
                LocalDateTime.now()
        );
        return ResponseEntity.status(ex.getHttpStatus()).body(body);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorRs> handleException(Exception e) {
        var body = new ErrorRs(
                INTERNAL_SERVER_ERROR,
                "Error",
                LocalDateTime.now()
        );

        log.error("Exception: ", e);

        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(body);
    }
}
