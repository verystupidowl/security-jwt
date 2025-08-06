package org.tggc.authenticationservice.util;

import lombok.experimental.UtilityClass;

import java.security.SecureRandom;

@UtilityClass
public class CodeGenerator {

    public String generate() {
        SecureRandom random = new SecureRandom();
        int code = 100_000 + random.nextInt(900_000);
        return String.valueOf(code);
    }
}
