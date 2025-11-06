package org.tggc.apigateway.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.tggc.apigateway.dto.TokenRs;
import org.tggc.authapi.api.AuthApi;
import org.tggc.authapi.dto.AuthenticationRq;
import org.tggc.authapi.dto.RegisterRq;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationService {
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    private final AuthApi authApi;
    private final JwtService jwtService;

    public Mono<TokenRs> register(RegisterRq rq) {
        return authApi.register(rq)
                .map(authenticationRs -> {
                    String token = jwtService.generateToken(
                            authenticationRs.email(),
                            authenticationRs.roles(),
                            authenticationRs.userId()
                    );
                    return new TokenRs(token);
                });

    }

    public Mono<TokenRs> authenticate(AuthenticationRq rq) {
        return authApi.authenticate(rq)
                .map(authenticationRs -> {
                    String token = jwtService.generateToken(
                            authenticationRs.email(),
                            authenticationRs.roles(),
                            authenticationRs.userId()
                    );
                    return new TokenRs(token);
                });
    }
}
