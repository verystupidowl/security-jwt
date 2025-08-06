package org.tggc.authenticationservice.dto.response;

import lombok.Builder;

@Builder
public record AuthenticationRs(
        String token
) {}
