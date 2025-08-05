package ru.tggc.securityjwt.dto.request;

public record ChangePasswordRq(String password, String passwordConfirmation, String email) {
}
