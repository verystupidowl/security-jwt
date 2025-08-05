package ru.tggc.securityjwt.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.tggc.securityjwt.dto.AuthenticationRq;
import ru.tggc.securityjwt.dto.AuthenticationRs;
import ru.tggc.securityjwt.dto.ChangePasswordDto;
import ru.tggc.securityjwt.dto.SendCodeDto;
import ru.tggc.securityjwt.dto.RegisterRq;
import ru.tggc.securityjwt.dto.VerifyDto;
import ru.tggc.securityjwt.service.AuthenticationService;
import ru.tggc.securityjwt.service.UserService;

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
    public ResponseEntity<Void> sendCode(@Valid @RequestBody SendCodeDto dto) {
        authenticationService.sendCode(dto);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/verify-code")
    public Boolean verifyCode(@RequestBody VerifyDto dto) {
        return authenticationService.verifyCode(dto);
    }

    @PostMapping("/change-password")
    public ResponseEntity<Void> changePassword(@Valid @RequestBody ChangePasswordDto dto) {
        authenticationService.changePassword(dto);
        return ResponseEntity.ok().build();
    }
}
