package org.tggc.authapi.api;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.tggc.authapi.config.FeignConfig;
import org.tggc.authapi.dto.AuthenticationRq;
import org.tggc.authapi.dto.AuthenticationRs;
import org.tggc.authapi.dto.RegisterRq;
import reactivefeign.spring.config.ReactiveFeignClient;
import reactor.core.publisher.Mono;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@ReactiveFeignClient(name = "auth-api", url = "http://localhost:8081/api/v1/auth", configuration = FeignConfig.class)
public interface AuthApi {

    @PostMapping(
            value = "/authenticate",
            consumes = APPLICATION_JSON_VALUE,
            produces = APPLICATION_JSON_VALUE
    )
    Mono<AuthenticationRs> authenticate(@Valid @RequestBody AuthenticationRq request);

    @PostMapping(
            value = "/register",
            consumes = APPLICATION_JSON_VALUE,
            produces = APPLICATION_JSON_VALUE
    )
    @ResponseStatus(CREATED)
    Mono<AuthenticationRs> register(@Valid @RequestBody RegisterRq request);
}
