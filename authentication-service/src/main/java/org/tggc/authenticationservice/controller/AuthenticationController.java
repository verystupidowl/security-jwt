package org.tggc.authenticationservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.tggc.authenticationservice.dto.request.AuthenticationRq;
import org.tggc.authenticationservice.dto.request.ChangePasswordRq;
import org.tggc.authenticationservice.dto.request.RegisterRq;
import org.tggc.authenticationservice.dto.request.SendCodeRq;
import org.tggc.authenticationservice.dto.request.VerifyRq;
import org.tggc.authenticationservice.dto.response.AuthenticationRs;
import org.tggc.authenticationservice.service.AuthenticationService;
import org.tggc.authenticationservice.service.UserService;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/api/v1/auth")
@CrossOrigin
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    private final UserService userService;

    @PostMapping("/register")
    @ResponseStatus(CREATED)
    public AuthenticationRs register(@Valid @RequestBody RegisterRq request) {
        return userService.register(request);
    }

    @PostMapping("/authenticate")
    public AuthenticationRs authenticate(@Valid @RequestBody AuthenticationRq request) {
        return authenticationService.authenticate(request);
    }

    @PostMapping("/send-code")
    public ResponseEntity<Void> sendCode(@Valid @RequestBody SendCodeRq dto) {
        authenticationService.sendCode(dto);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/verify-code")
    public Boolean verifyCode(@RequestBody VerifyRq dto) {
        return authenticationService.verifyCode(dto);
    }

    @PostMapping("/change-password")
    public ResponseEntity<Void> changePassword(@Valid @RequestBody ChangePasswordRq dto) {
        authenticationService.changePassword(dto);
        return ResponseEntity.ok().build();
    }
}
