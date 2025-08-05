package ru.tggc.securityjwt.service;

import ru.tggc.securityjwt.dto.request.AuthenticationRq;
import ru.tggc.securityjwt.dto.response.AuthenticationRs;
import ru.tggc.securityjwt.dto.request.ChangePasswordRq;
import ru.tggc.securityjwt.dto.request.RegisterRq;
import ru.tggc.securityjwt.dto.request.SendCodeRq;
import ru.tggc.securityjwt.dto.request.VerifyRq;

public interface AuthenticationService {

    AuthenticationRs register(RegisterRq request);

    AuthenticationRs authenticate(AuthenticationRq request);

    void sendCode(SendCodeRq email);

    Boolean verifyCode(VerifyRq dto);

    void changePassword(ChangePasswordRq dto);
}
