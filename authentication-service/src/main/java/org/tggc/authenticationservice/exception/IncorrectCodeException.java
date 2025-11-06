package org.tggc.authenticationservice.exception;

import static org.tggc.authenticationservice.exception.message.ExceptionMessage.INCORRECT_CODE;

public class IncorrectCodeException extends BadRequestException {

    public IncorrectCodeException() {
        super(INCORRECT_CODE);
    }
}
