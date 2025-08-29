package org.tggc.apigateway.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.tggc.apigateway.dto.TokenRs;
import org.tggc.apigateway.service.AuthenticationService;
import org.tggc.authapi.dto.AuthenticationRq;
import org.tggc.authapi.dto.RegisterRq;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public Mono<TokenRs> register(@RequestBody RegisterRq rq) {
        return authenticationService.register(rq);
    }

    @PostMapping("/authenticate")
    public Mono<TokenRs> authenticate(@RequestBody AuthenticationRq rq) {
        return authenticationService.authenticate(rq);
    }
}
