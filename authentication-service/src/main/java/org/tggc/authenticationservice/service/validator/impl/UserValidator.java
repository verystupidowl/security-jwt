package org.tggc.authenticationservice.service.validator.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.tggc.authenticationservice.exception.IncorrectPasswordException;
import org.tggc.authenticationservice.exception.UserBlockedException;
import org.tggc.authenticationservice.model.User;
import org.tggc.authenticationservice.service.PasswordService;
import org.tggc.authenticationservice.service.validator.Validator;
import org.tggc.authenticationservice.service.validator.rq.ValidationRq;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UserValidator implements Validator<ValidationRq<User, String>, User> {
    private final PasswordService passwordService;

    @Override
    public Mono<User> validate(ValidationRq<User, String> rq) {
        User user = rq.v1();
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
