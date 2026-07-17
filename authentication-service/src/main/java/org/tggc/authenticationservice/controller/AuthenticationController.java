package org.tggc.authenticationservice.controller;

import jakarta.validation.Valid;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.tggc.authapi.api.AuthApi;
import org.tggc.authapi.dto.AuthenticationRq;
import org.tggc.authapi.dto.AuthenticationRs;
import org.tggc.authapi.dto.RegisterRq;
import org.tggc.authenticationservice.dto.request.ChangePasswordRq;
import org.tggc.authenticationservice.dto.request.SendCodeRq;
import org.tggc.authenticationservice.service.AuthenticationService;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthenticationController implements AuthApi {
    private final AuthenticationService authenticationService;

    @Override
    public Mono<@NonNull AuthenticationRs> register(@Valid @RequestBody RegisterRq request) {
        return authenticationService.register(request);
    }

    @Override
    public Mono<@NonNull AuthenticationRs> authenticate(@Valid @RequestBody AuthenticationRq request) {
        return authenticationService.authenticate(request);
    }

    @PostMapping("/send-code")
    public Mono<@NonNull Void> sendCode(@Valid @RequestBody SendCodeRq dto) {
        return authenticationService.sendCode(dto);
    }

    @PostMapping("/change-password")
    public Mono<@NonNull Void> changePassword(@Valid @RequestBody ChangePasswordRq dto) {
        return authenticationService.changePassword(dto);
    }

    @PostMapping("/block/{userId}")
    public Mono<@NonNull Void> blockUser(@PathVariable("userId") Long userId,
                                @RequestParam("block") Boolean block,
                                @RequestHeader("X-UserId") Long id) {
        return authenticationService.blockUser(userId, block, id);
    }
}
