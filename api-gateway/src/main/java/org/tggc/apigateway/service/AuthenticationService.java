package org.tggc.apigateway.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.tggc.apigateway.dto.TokenRs;
import org.tggc.authapi.api.AuthApi;
import org.tggc.authapi.dto.AuthenticationRq;
import org.tggc.authapi.dto.AuthenticationRs;
import org.tggc.authapi.dto.RegisterRq;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final JwtService jwtService;
    private final AuthApi authApi;

    public TokenRs register(RegisterRq rq) {
        AuthenticationRs register = authApi.register(rq);
        String token = jwtService.generateToken(register.email(), register.roles());
        return new TokenRs(token);
    }

    public TokenRs authenticate(AuthenticationRq rq) {
        AuthenticationRs authenticate = authApi.authenticate(rq);
        String token = jwtService.generateToken(authenticate.email(), authenticate.roles());
        return new TokenRs(token);
    }
}
