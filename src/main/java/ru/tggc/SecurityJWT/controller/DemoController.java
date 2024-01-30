package ru.tggc.SecurityJWT.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/demo-controller")
@CrossOrigin
public class DemoController {

    @GetMapping
    public ResponseEntity<String> sayHello(UsernamePasswordAuthenticationToken principal) {
        System.out.println(principal);
        return ResponseEntity.ok("Hello from secured controller!");
    }
}
