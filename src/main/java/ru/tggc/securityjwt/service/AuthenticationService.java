package ru.tggc.securityjwt.service;

import ru.tggc.securityjwt.dto.AuthenticationRq;
import ru.tggc.securityjwt.dto.AuthenticationRs;
import ru.tggc.securityjwt.dto.ChangePasswordDto;
import ru.tggc.securityjwt.dto.RegisterRq;
import ru.tggc.securityjwt.dto.SendCodeDto;
import ru.tggc.securityjwt.dto.VerifyDto;

public interface AuthenticationService {

    AuthenticationRs register(RegisterRq request);

    AuthenticationRs authenticate(AuthenticationRq request);

    void sendCode(SendCodeDto email);

    Boolean verifyCode(VerifyDto dto);

    void changePassword(ChangePasswordDto dto);
}
