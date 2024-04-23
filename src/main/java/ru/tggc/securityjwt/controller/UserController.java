package ru.tggc.securityjwt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.tggc.securityjwt.dto.UserDTO;
import ru.tggc.securityjwt.model.User;
import ru.tggc.securityjwt.util.mapper.UserMapper;

@RestController
@RequestMapping("/api/v1/user")
@CrossOrigin
public class UserController {

    private final UserMapper userMapper;

    @Autowired
    public UserController(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @GetMapping
    public UserDTO findUser(UsernamePasswordAuthenticationToken token) {
        User user = (User) token.getPrincipal();
        return userMapper.toDto(user);
    }
}
