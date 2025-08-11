package org.tggc.authenticationservice.service;

import org.tggc.authapi.dto.AuthenticationRq;
import org.tggc.authapi.dto.AuthenticationRs;
import org.tggc.authapi.dto.RegisterRq;
import org.tggc.authenticationservice.dto.request.ChangePasswordRq;
import org.tggc.authenticationservice.dto.request.SendCodeRq;
import org.tggc.authenticationservice.dto.request.VerifyRq;
import reactor.core.publisher.Mono;

public interface AuthenticationService {

    Mono<AuthenticationRs> register(RegisterRq request);

    Mono<AuthenticationRs> authenticate(AuthenticationRq request);

    Mono<Void> sendCode(SendCodeRq email);

    Mono<Boolean> verifyCode(VerifyRq dto);

    Mono<Void> changePassword(ChangePasswordRq dto);
}
