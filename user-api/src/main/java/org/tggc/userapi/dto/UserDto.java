package org.tggc.userapi.dto;

import lombok.Builder;

@Builder
public record UserDto(
        String firstname,
        String lastname,
        String email,
        String role
) {
}