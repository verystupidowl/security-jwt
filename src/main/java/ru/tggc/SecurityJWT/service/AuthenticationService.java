package ru.tggc.SecurityJWT.service;

import ru.tggc.SecurityJWT.dto.AuthenticationRequest;
import ru.tggc.SecurityJWT.dto.AuthenticationResponse;
import ru.tggc.SecurityJWT.dto.RegisterRequest;

public interface AuthenticationService {


    AuthenticationResponse register(RegisterRequest request);

    AuthenticationResponse authenticate(AuthenticationRequest request);
}
