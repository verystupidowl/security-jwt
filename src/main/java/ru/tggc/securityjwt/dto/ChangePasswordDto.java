package ru.tggc.securityjwt.dto;

public record ChangePasswordDto(String password, String passwordConfirmation, String email) {
}
