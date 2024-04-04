package ru.tggc.SecurityJWT.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
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
@Slf4j
public class DemoController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<String> sayHello() {
        return ResponseEntity.ok("Hello from secured controller!");
    }

    @GetMapping("/get")
    public List<User> getAll() {
        return userService.findAll();
    }

    @PostMapping("/")
    public ResponseEntity<Void> saveUser(@RequestBody UserDTO dto) {
        userService.save(dto);
        return new ResponseEntity<>(CREATED);
    }
}
