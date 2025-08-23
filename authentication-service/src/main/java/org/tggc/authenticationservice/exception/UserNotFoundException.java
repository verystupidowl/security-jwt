package org.tggc.authenticationservice.exception;

import org.tggc.authenticationservice.exception.message.ExceptionMessage;

public class UserNotFoundException extends BadRequestException {

    public UserNotFoundException(String email) {
        super(ExceptionMessage.USER_NOT_FOUND, email);
    }
}
