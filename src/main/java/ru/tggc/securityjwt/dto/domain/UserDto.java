package ru.tggc.securityjwt.dto.domain;

import lombok.Builder;

@Builder
public record UserDto(
        String firstname,
        String lastname,
        String email
) {
}
