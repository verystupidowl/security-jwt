package org.tggc.userservice.controller;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import org.tggc.authapi.dto.AuthenticationRs;
import org.tggc.authapi.dto.RegisterRq;
import org.tggc.userapi.api.AuthenticationApi;
import org.tggc.userapi.dto.UserDto;
import org.tggc.userservice.service.UserService;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class AuthenticationController implements AuthenticationApi {
    private final UserService userService;

    @Override
    public Mono<@NonNull UserDto> getUserByEmail(String email) {
        return userService.getUserByEmail(email);
    }

    @Override
    public Mono<@NonNull AuthenticationRs> saveUser(RegisterRq registerRq) {
        return userService.saveUser(registerRq);
    }
}
