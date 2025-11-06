package org.tggc.authapi.dto;

import lombok.Builder;

@Builder
public record AuthenticationRq(String email, String password) {
}
