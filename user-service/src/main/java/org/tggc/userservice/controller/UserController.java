package org.tggc.userservice.controller;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.tggc.userapi.api.UserApi;
import org.tggc.userapi.dto.UserDto;
import org.tggc.userservice.service.UserService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController implements UserApi {
    private final UserService userService;

    @GetMapping("/me")
    public Mono<@NonNull UserDto> getMe(@RequestHeader("X-User-Id") Long userId) {
        return userService.getUserById(userId);
    }

    @Override
    public Mono<@NonNull UserDto> getUserById(long id) {
        return userService.getUserById(id);
    }

    @Override
    public Flux<@NonNull UserDto> getUsers(List<Long> ids) {
        return userService.getUsersByIds(ids);
    }
}
