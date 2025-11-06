package org.tggc.authenticationservice.exception;

import static org.tggc.authenticationservice.exception.message.ExceptionMessage.USER_NOT_FOUND;

public class UserNotFoundException extends BadRequestException {

    public UserNotFoundException(String email) {
        super(USER_NOT_FOUND, email);
    }
}
