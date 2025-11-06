package org.tggc.authenticationservice.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.tggc.authenticationservice.exception.message.ExceptionMessage;

@Getter
public class GlobalException extends RuntimeException {
    private final HttpStatus httpStatus;

    public GlobalException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public GlobalException(ExceptionMessage exceptionMessage, HttpStatus httpStatus) {
        super(exceptionMessage.getMessage());
        this.httpStatus = httpStatus;
    }

    public GlobalException(ExceptionMessage exceptionMessage, HttpStatus httpStatus, Object... formatted) {
        super(exceptionMessage.getMessage().formatted(formatted));
        this.httpStatus = httpStatus;
    }
}
