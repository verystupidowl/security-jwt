package ru.tggc.securityjwt.dto;

import lombok.Builder;


@Builder
public record AuthenticationRequest(
        String email,
        String password) {}
