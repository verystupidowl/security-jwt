package ru.tggc.securityjwt.dto;

import lombok.Builder;


@Builder
public record AuthenticationRq(
        String email,
        String password) {
}
