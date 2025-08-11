package org.tggc.userservice.controller;

import org.springframework.web.bind.annotation.RestController;
import org.tggc.userapi.api.UserApi;
import org.tggc.userapi.dto.UserDto;
import org.tggc.userservice.service.UserService;

import java.util.List;

@RestController
public class UserController implements UserApi {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDto getUserById(long id) {
        return userService.getUserById(id);
    }

    @Override
    public List<UserDto> getUsers(List<Long> ids) {
        return userService.getUsersByIds(ids);
    }
}
