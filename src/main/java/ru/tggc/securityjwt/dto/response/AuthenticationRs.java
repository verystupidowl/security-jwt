package ru.tggc.securityjwt.dto.response;

import lombok.Builder;

@Builder
public record AuthenticationRs(
        String token
) {}
