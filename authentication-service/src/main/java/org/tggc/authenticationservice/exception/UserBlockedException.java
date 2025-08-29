package org.tggc.authenticationservice.exception;

import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.tggc.authenticationservice.exception.message.ExceptionMessage.USER_BLOCKED;

public class UserBlockedException extends GlobalException {

    public UserBlockedException() {
        super(USER_BLOCKED, FORBIDDEN);
    }
}
