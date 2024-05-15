package ru.tggc.securityjwt.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.tggc.securityjwt.dto.AuthenticationRequest;
import ru.tggc.securityjwt.dto.AuthenticationResponse;
import ru.tggc.securityjwt.dto.RegisterRequest;
import ru.tggc.securityjwt.exception.UserAlreadyCreatedException;
import ru.tggc.securityjwt.service.AuthenticationService;
import ru.tggc.securityjwt.service.UserService;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/api/v1/auth")
@CrossOrigin
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final UserService userService;

    @Autowired
    public AuthenticationController(AuthenticationService authenticationService, UserService userService) {
        this.authenticationService = authenticationService;
        this.userService = userService;
    }

    @PostMapping("/register")
    @ResponseStatus(CREATED)
    public AuthenticationResponse register(@Valid @RequestBody RegisterRequest request) {
        userService.findByEmail(request.email())
                .ifPresent(u -> {
                    throw new UserAlreadyCreatedException(STR."User with email \{u.getEmail()} have been already created");
                });
        return authenticationService.register(request);
    }

    @PostMapping("/authenticate")
    public AuthenticationResponse authenticate(@Valid @RequestBody AuthenticationRequest request) {
        return authenticationService.authenticate(request);
    }

}
