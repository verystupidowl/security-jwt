package org.tggc.userservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.tggc.userapi.api.UserApi;
import org.tggc.userapi.dto.UserDto;
import org.tggc.userservice.service.UserService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController implements UserApi {
    private final UserService userService;

    @Override
    public UserDto getUserById(long id) {
        return userService.getUserById(id);
    }

    @Override
    public List<UserDto> getUsers(List<Long> ids) {
        return userService.getUsersByIds(ids);
    }
}
