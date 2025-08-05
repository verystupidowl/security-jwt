package ru.tggc.securityjwt.dto.request;

import lombok.Builder;


@Builder
public record AuthenticationRq(
        String email,
        String password) {
}
