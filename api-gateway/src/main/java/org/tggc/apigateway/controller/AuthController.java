package org.tggc.apigateway.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.tggc.apigateway.dto.TokenRs;
import org.tggc.apigateway.service.AuthenticationService;
import org.tggc.authapi.dto.AuthenticationRq;
import org.tggc.authapi.dto.RegisterRq;

@RestController
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<TokenRs> register(@RequestBody RegisterRq rq) {
        return ResponseEntity.ok(authenticationService.register(rq));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<TokenRs> authenticate(@RequestBody AuthenticationRq rq) {
        return ResponseEntity.ok(authenticationService.authenticate(rq));
    }
}
