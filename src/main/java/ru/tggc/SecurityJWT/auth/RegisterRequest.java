package ru.tggc.SecurityJWT.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    @NotEmpty(message = "Firstname should not be empty!")
    @Size(min = 1, max = 20, message = "Firstname size should be between 1 and 20 characters!")
    private String firstname;

    @NotEmpty(message = "Lastname should not be empty!")
    @Size(min = 1, max = 20, message = "Lastname size should be between 1 and 20 characters!")
    private String lastname;

    @Email(message = "Email should be valid!")
    @NotEmpty(message = "Email should not be empty!")
    @Size(min = 1, max = 100, message = "Email size should be between 1 and 100 characters!")
    private String email;

    @NotEmpty
    private String password;
}
