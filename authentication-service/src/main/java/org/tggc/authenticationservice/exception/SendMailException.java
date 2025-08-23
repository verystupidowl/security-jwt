package org.tggc.authenticationservice.exception;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

public class SendMailException extends GlobalException {

    public SendMailException(String message, Throwable cause) {
        super(message, INTERNAL_SERVER_ERROR, cause);
    }
}
