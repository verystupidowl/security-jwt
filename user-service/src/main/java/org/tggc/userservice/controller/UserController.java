package org.tggc.userservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.tggc.userapi.api.UserApi;
import org.tggc.userapi.dto.UserDto;
import org.tggc.userservice.aop.annotation.RequiresRoles;
import org.tggc.userservice.model.Role;
import org.tggc.userservice.service.UserService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController implements UserApi {
    private final UserService userService;

    @GetMapping("/me")
    public UserDto getMe(@RequestHeader("X-User-Id") Long userId) {
        return userService.getUserById(userId);
    }

    @PostMapping("/block/{userId}")
    @RequiresRoles({Role.ADMIN})
    public ResponseEntity<Void> blockUser(@PathVariable("userId") Long userId, @RequestParam("block") Boolean block) {
        userService.blockUser(userId, block);
        return ResponseEntity.ok().build();
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
