package org.tggc.authapi.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class FeignErrorException extends RuntimeException {
    private static final String MESSAGE = "Feign Error";
    private final HttpStatus httpStatus;
    private final String body;

    public FeignErrorException(HttpStatus httpStatus, String body) {
        super(MESSAGE);
        this.httpStatus = httpStatus;
        this.body = body;
    }
}
