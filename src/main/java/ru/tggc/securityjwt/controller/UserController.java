package ru.tggc.securityjwt.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.tggc.securityjwt.dto.domain.UserDto;
import ru.tggc.securityjwt.model.User;
import ru.tggc.securityjwt.mapper.UserMapper;

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
