package org.tggc.authenticationservice.dto.request;

import lombok.Builder;


@Builder
public record AuthenticationRq(
        String email,
        String password) {
}
