package org.tggc.authapi.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record RegisterRq(
        @NotEmpty(message = "Firstname should not be empty!")
        @Size(min = 1, max = 20, message = "Firstname size should be between 1 and 20 characters!")
        String firstname,

        @NotEmpty(message = "Lastname should not be empty!")
        @Size(min = 1, max = 20, message = "Lastname size should be between 1 and 20 characters!")
        String lastname,

        @Email(message = "Email should be valid!")
        @NotEmpty(message = "Email should not be empty!")
        @Size(min = 1, max = 100, message = "Email size should be between 1 and 100 characters!")
        String email,

        @NotEmpty
        String password,
        @NotEmpty
        String passwordConfirmation,
        @NotNull
        Boolean twoFactorEnabled
) {
}
