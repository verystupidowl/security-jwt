package org.tggc.authenticationservice.exception;

import static org.tggc.authenticationservice.exception.message.ExceptionMessage.USER_ALREADY_CREATED;

public class UserAlreadyCreatedException extends BadRequestException {

    public UserAlreadyCreatedException(String email) {
        super(USER_ALREADY_CREATED, email);
    }
}
