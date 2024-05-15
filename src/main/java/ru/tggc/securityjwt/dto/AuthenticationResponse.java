package ru.tggc.securityjwt.dto;

import lombok.Builder;

@Builder
public record AuthenticationResponse(
        String token
) {}
