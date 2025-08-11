package org.tggc.authenticationservice.dto.domain;

import lombok.Builder;

@Builder
public record UserRs(
        String firstname,
        String lastname,
        String email
) {
}
