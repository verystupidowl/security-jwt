package org.tggc.authenticationservice.exception;

import org.springframework.http.HttpStatus;
import org.tggc.authenticationservice.exception.message.ExceptionMessage;

public class UserForbiddenException extends GlobalException {

    public UserForbiddenException(String email) {
        super(ExceptionMessage.USER_HAS_NO_ROLE, HttpStatus.FORBIDDEN, email);
    }
}
