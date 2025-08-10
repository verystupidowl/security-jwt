package org.tggc.authenticationservice.service.impl;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.tggc.authenticationservice.service.PasswordService;

@Service
public class PasswordServiceImpl implements PasswordService {

    @Override
    public String hash(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    @Override
    public boolean checkPassword(String password, String hash) {
        return BCrypt.checkpw(password, hash);
    }
}
