package org.tggc.authenticationservice.dto.request;

public record ChangePasswordRq(String password, String passwordConfirmation, String email) {
}
