package ru.tggc.SecurityJWT.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import ru.tggc.SecurityJWT.dto.UserDTO;
import ru.tggc.SecurityJWT.model.User;
import ru.tggc.SecurityJWT.service.UserService;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/api/v1/demo-controller")
@CrossOrigin
@RequiredArgsConstructor
public class DemoController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<String> sayHello(UsernamePasswordAuthenticationToken principal) {
        System.out.println(principal);
        return ResponseEntity.ok("Hello from secured controller!");
    }

    @GetMapping("/get")
    public List<User> getAll() {
        return userService.findAll();
    }

    @PostMapping("/")
    public ResponseEntity<Void> saveUser(UserDTO dto) {
        userService.save(dto);
        return new ResponseEntity<>(CREATED);
    }
}
