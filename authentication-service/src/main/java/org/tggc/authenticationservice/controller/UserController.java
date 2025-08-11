package org.tggc.authenticationservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.tggc.authenticationservice.dto.domain.UserRs;
import org.tggc.authenticationservice.service.UserService;
import org.tggc.userapi.api.UserApi;
import org.tggc.userapi.dto.UserDto;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController implements UserApi {
    private final UserService userService;

    @Override
    public UserDto getUserById(long id) {
        return userService.getById(id);
    }

    @Override
    public List<UserDto> getUsers(List<Long> ids) {
        return userService.getByIds(ids);
    }

    @Override
    public String getEmailById(Long userId) {
        return userService.getEmailById(userId);
    }

    @GetMapping
    public UserRs findUser(@RequestHeader("X-User-Name") String email) {
        return userService.getByEmail(email);
    }
}
