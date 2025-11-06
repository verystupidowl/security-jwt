package org.tggc.authenticationservice.service.validator.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.tggc.authenticationservice.exception.IncorrectCodeException;
import org.tggc.authenticationservice.service.validator.Validator;
import org.tggc.authenticationservice.service.validator.rq.ValidationRq;
import org.tggc.notificationapi.api.CodeApi;
import org.tggc.notificationapi.dto.NotificationType;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Service
@RequiredArgsConstructor
public class EmailCodeValidator implements Validator<ValidationRq<String, String>, Void> {
    private final CodeApi codeApi;

    @Override
    public Mono<Void> validate(ValidationRq<String, String> rq) {
        String email = rq.v1();
        String code = rq.v2();
        return getCode(email)
                .flatMap(actual -> {
                    if (!code.equals(actual)) {
                        return Mono.error(new IncorrectCodeException());
                    }
                    return Mono.empty();
                });
    }

    private Mono<String> getCode(String email) {
        return Mono.fromCallable(() -> codeApi.getCode(email, NotificationType.EMAIL_CONFIRMATION))
                .subscribeOn(Schedulers.boundedElastic());
    }
}
