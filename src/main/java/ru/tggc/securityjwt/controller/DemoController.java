package ru.tggc.securityjwt.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.tggc.securityjwt.service.UserService;

@RestController
@RequestMapping("/api/v1/demo-controller")
@CrossOrigin
@RequiredArgsConstructor
@Slf4j
public class DemoController {

    private final UserService userService;
//
//    @GetMapping
//    public ResponseEntity<String> sayHello() {
//        return ResponseEntity.ok("Hello from secured controller!");
//    }
//
//    @GetMapping
//    public List<User> getAll() {
//        return userService.findAll();
//    }
//
//    @PostMapping
//    public ResponseEntity<Void> saveUser(@RequestBody UserDTO dto) {
//        userService.save(dto);
//        return new ResponseEntity<>(CREATED);
//    }
}
