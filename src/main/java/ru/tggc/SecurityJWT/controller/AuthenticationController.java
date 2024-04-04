package ru.tggc.SecurityJWT.controller;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.tggc.SecurityJWT.auth.AuthenticationRequest;
import ru.tggc.SecurityJWT.auth.AuthenticationResponse;
import ru.tggc.SecurityJWT.auth.RegisterRequest;
import ru.tggc.SecurityJWT.dto.ErrorDTO;
import ru.tggc.SecurityJWT.exception.UserAlreadyCreatedException;
import ru.tggc.SecurityJWT.service.AuthenticationService;
import ru.tggc.SecurityJWT.service.UserService;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

import static java.lang.StringTemplate.STR;

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
    public AuthenticationResponse register(@Valid @RequestBody RegisterRequest request) {
        userService.findByEmail(request.getEmail())
                .ifPresent(u -> {
                    throw new UserAlreadyCreatedException(STR."User with email \{u.getEmail()} have been already created");
                });
        return authenticationService.register(request);
    }

    @PostMapping("/authenticate")
    public AuthenticationResponse authenticate(@Valid @RequestBody AuthenticationRequest request) {
        return authenticationService.authenticate(request);
    }

    @ExceptionHandler(value = {UserAlreadyCreatedException.class})
    public ResponseEntity<ErrorDTO> handleUserAlreadyCreatedException(UserAlreadyCreatedException e) {
        ErrorDTO errorDTO = new ErrorDTO(e.getMessage(), LocalDateTime.now());
        return ResponseEntity.badRequest().body(errorDTO);
    }

    @ExceptionHandler(value = {ConstraintViolationException.class})
    public ResponseEntity<ErrorDTO> handleMethodArgumentNotValidException(ConstraintViolationException e) {
        ErrorDTO errorDTO = new ErrorDTO(
                e.getConstraintViolations()
                        .stream()
                        .map(ConstraintViolation::getMessageTemplate)
                        .collect(Collectors.joining(", ")),
                LocalDateTime.now()
        );
        return ResponseEntity.badRequest().body(errorDTO);
    }
}
