package org.tggc.authenticationservice.exception;

import org.tggc.authenticationservice.exception.message.ExceptionMessage;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

public class BadRequestException extends GlobalException {

    public BadRequestException(ExceptionMessage exceptionMessage) {
        super(exceptionMessage.getMessage(), BAD_REQUEST);
    }

    public BadRequestException(ExceptionMessage exceptionMessage, Object... formatted) {
        super(exceptionMessage, BAD_REQUEST, formatted);
    }
}
