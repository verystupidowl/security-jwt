package ru.tggc.securityjwt.dto;

import lombok.Builder;

@Builder
public record UserDTO(

        String firstname,
        String lastname,
        String email
) {
}
