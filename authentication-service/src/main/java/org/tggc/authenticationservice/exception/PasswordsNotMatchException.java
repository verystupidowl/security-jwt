package org.tggc.authenticationservice.exception;

import static org.tggc.authenticationservice.exception.message.ExceptionMessage.PASSWORDS_NOT_MATCH;

public class PasswordsNotMatchException extends BadRequestException {

    public PasswordsNotMatchException() {
        super(PASSWORDS_NOT_MATCH);
    }
}
