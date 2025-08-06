package org.tggc.authenticationservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.tggc.authenticationservice.dto.domain.UserDto;
import org.tggc.authenticationservice.model.User;
import org.tggc.authenticationservice.mapper.UserMapper;

@RestController
@RequestMapping("/api/v1/user")
@CrossOrigin
@RequiredArgsConstructor
public class UserController {
    private final UserMapper userMapper;

    @GetMapping
    public UserDto findUser(UsernamePasswordAuthenticationToken token) {
        User user = (User) token.getPrincipal();
        return userMapper.toDto(user);
    }
}
