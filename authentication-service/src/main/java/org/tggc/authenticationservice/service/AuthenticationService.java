package org.tggc.authenticationservice.service;

import lombok.NonNull;
import org.tggc.authapi.dto.AuthenticationRq;
import org.tggc.authapi.dto.AuthenticationRs;
import org.tggc.authapi.dto.RegisterRq;
import org.tggc.authenticationservice.dto.request.ChangePasswordRq;
import org.tggc.authenticationservice.dto.request.SendCodeRq;
import reactor.core.publisher.Mono;

public interface AuthenticationService {

    Mono<@NonNull AuthenticationRs> register(RegisterRq request);

    Mono<@NonNull AuthenticationRs> authenticate(AuthenticationRq request);

    Mono<@NonNull Void> sendCode(SendCodeRq email);

    Mono<@NonNull Void> changePassword(ChangePasswordRq dto);

    Mono<@NonNull Void> blockUser(Long userId, Boolean block, Long id);
}
