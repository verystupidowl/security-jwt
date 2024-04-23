package ru.tggc.securityjwt.service;

import ru.tggc.securityjwt.dto.AuthenticationRequest;
import ru.tggc.securityjwt.dto.AuthenticationResponse;
import ru.tggc.securityjwt.dto.RegisterRequest;

public interface AuthenticationService {


    AuthenticationResponse register(RegisterRequest request);

    AuthenticationResponse authenticate(AuthenticationRequest request);
}
