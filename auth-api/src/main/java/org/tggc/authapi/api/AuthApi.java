package org.tggc.authapi.api;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.tggc.authapi.dto.AuthenticationRq;
import org.tggc.authapi.dto.AuthenticationRs;
import org.tggc.authapi.dto.RegisterRq;
import reactivefeign.spring.config.ReactiveFeignClient;
import reactor.core.publisher.Mono;

import static org.springframework.http.HttpStatus.CREATED;

@ReactiveFeignClient(name = "auth-api", url = "http://localhost:8081/auth")
public interface AuthApi {

    @PostMapping("/authenticate")
    Mono<AuthenticationRs> authenticate(@Valid @RequestBody AuthenticationRq request);

    @PostMapping("/register")
    @ResponseStatus(CREATED)
    Mono<AuthenticationRs> register(@Valid @RequestBody RegisterRq request);
}
