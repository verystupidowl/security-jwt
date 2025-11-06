package org.tggc.authenticationservice.service.validator;

import reactor.core.publisher.Mono;

public interface Validator<I, O> {

    Mono<O> validate(I input);
}
