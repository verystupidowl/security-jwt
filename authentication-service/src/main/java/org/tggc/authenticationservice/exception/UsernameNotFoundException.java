package org.tggc.authenticationservice.exception;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;
import static org.tggc.authenticationservice.exception.message.ExceptionMessage.USER_NOT_FOUND;

public class UsernameNotFoundException extends GlobalException {

    public UsernameNotFoundException(String email) {
        super(USER_NOT_FOUND, UNAUTHORIZED, email);
    }
}
