package org.tggc.apigateway.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.tggc.authapi.dto.ErrorRs;
import org.tggc.authapi.exception.FeignErrorException;

@RestControllerAdvice
@RequiredArgsConstructor
public class FeignExceptionHandler {
    private final ObjectMapper objectMapper;

    @ExceptionHandler(FeignErrorException.class)
    @SneakyThrows
    public ResponseEntity<ErrorRs> handleFeignClientException(FeignErrorException e) {
        return new ResponseEntity<>(objectMapper.readValue(e.getBody(), ErrorRs.class), e.getHttpStatus());
    }
}
