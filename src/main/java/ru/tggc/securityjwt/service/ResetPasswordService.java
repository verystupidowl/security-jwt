package ru.tggc.securityjwt.service;

public interface ResetPasswordService {

    String getCode(String email);

    void sendCode(String email, String text);

    void deleteCode(String email);
}
