package ru.tggc.SecurityJWT.service;

import ru.tggc.SecurityJWT.auth.AuthenticationRequest;
import ru.tggc.SecurityJWT.auth.AuthenticationResponse;
import ru.tggc.SecurityJWT.auth.RegisterRequest;

public interface AuthenticationService {


    AuthenticationResponse register(RegisterRequest request);

    AuthenticationResponse authenticate(AuthenticationRequest request);
}
