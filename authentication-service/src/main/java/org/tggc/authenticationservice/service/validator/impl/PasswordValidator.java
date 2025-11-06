package org.tggc.authenticationservice.service.validator.impl;

import org.springframework.stereotype.Service;
import org.tggc.authenticationservice.exception.PasswordsNotMatchException;
import org.tggc.authenticationservice.service.validator.Validator;
import org.tggc.authenticationservice.service.validator.rq.ValidationRq;
import reactor.core.publisher.Mono;

@Service
public class PasswordValidator implements Validator<ValidationRq<String, String>, Void> {

    @Override
    public Mono<Void> validate(ValidationRq<String, String> rq) {
        String password = rq.v1();
        String passwordConfirmation = rq.v2();
        if (!password.equals(passwordConfirmation)) {
            return Mono.error(new PasswordsNotMatchException());
        }
        return Mono.empty();
    }
}
