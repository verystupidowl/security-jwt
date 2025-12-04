package org.tggc.authenticationservice.service.validator.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.tggc.authenticationservice.exception.IncorrectPasswordException;
import org.tggc.authenticationservice.exception.UserBlockedException;
import org.tggc.authenticationservice.model.UserCredentials;
import org.tggc.authenticationservice.service.PasswordService;
import org.tggc.authenticationservice.service.validator.Validator;
import org.tggc.authenticationservice.service.validator.rq.ValidationRq;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UserValidator implements Validator<ValidationRq<UserCredentials, String>, UserCredentials> {
    private final PasswordService passwordService;

    @Override
    public Mono<UserCredentials> validate(ValidationRq<UserCredentials, String> rq) {
        UserCredentials user = rq.v1();
        String password = rq.v2();
        if (Boolean.TRUE.equals(user.getBlocked())) {
            return Mono.error(new UserBlockedException());
        }
        if (!passwordService.checkPassword(password, user.getPassword())) {
            return Mono.error(new IncorrectPasswordException());
        }
        return Mono.just(user);
    }
}
