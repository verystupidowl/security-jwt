package org.tggc.authenticationservice.exception;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;
import static org.tggc.authenticationservice.exception.message.ExceptionMessage.INCORRECT_PASSWORD;

public class IncorrectPasswordException extends GlobalException {

    public IncorrectPasswordException() {
        super(INCORRECT_PASSWORD, UNAUTHORIZED);
    }
}
