package org.tggc.authenticationservice.service;

import org.tggc.authapi.dto.AuthenticationRq;
import org.tggc.authapi.dto.AuthenticationRs;
import org.tggc.authapi.dto.RegisterRq;
import org.tggc.authenticationservice.dto.request.ChangePasswordRq;
import org.tggc.authenticationservice.dto.request.SendCodeRq;
import org.tggc.authenticationservice.dto.request.VerifyRq;

public interface AuthenticationService {

    AuthenticationRs register(RegisterRq request);

    AuthenticationRs authenticate(AuthenticationRq request);

    void sendCode(SendCodeRq email);

    Boolean verifyCode(VerifyRq dto);

    void changePassword(ChangePasswordRq dto);
}
