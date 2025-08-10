package org.tggc.authenticationservice.service;

public interface PasswordService {

    String hash(String password);

    boolean checkPassword(String password, String hashedPassword);
}
