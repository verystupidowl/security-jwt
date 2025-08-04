package ru.tggc.securityjwt.dto;

import lombok.Builder;

@Builder
public record AuthenticationRs(
        String token
) {}
