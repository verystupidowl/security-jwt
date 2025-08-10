package org.tggc.authenticationservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.tggc.authenticationservice.dto.domain.UserDto;
import org.tggc.authenticationservice.service.UserService;

@RestController
@RequestMapping("/api/v1/user")
@CrossOrigin
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    public UserDto findUser(@RequestHeader("X-User-Name") String email) {
        return userService.getByEmail(email);
    }
}
