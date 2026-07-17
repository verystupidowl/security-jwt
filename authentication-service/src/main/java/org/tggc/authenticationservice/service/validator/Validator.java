package org.tggc.authenticationservice.service.validator;

import org.jspecify.annotations.NonNull;
import reactor.core.publisher.Mono;

public interface Validator<I, O> {

    Mono<@NonNull O> validate(I input);
}
