package ru.tggc.SecurityJWT.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.tggc.SecurityJWT.dto.UserDTO;
import ru.tggc.SecurityJWT.model.User;
import ru.tggc.SecurityJWT.util.UserMapper;

@RestController
@RequestMapping("/api/v1/user")
@CrossOrigin
public class UserController {

    private final UserMapper userMapper;

    @Autowired
    public UserController(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @GetMapping("/getUser")
    public UserDTO findUser(UsernamePasswordAuthenticationToken token) {
        User user = (User) token.getPrincipal();
        return userMapper.fromUserEntityToUserDTO(user);
    }
}
