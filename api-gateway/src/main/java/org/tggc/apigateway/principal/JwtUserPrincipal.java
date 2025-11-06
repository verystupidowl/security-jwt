package org.tggc.apigateway.principal;

import lombok.Builder;

@Builder
public record JwtUserPrincipal(Long id, String email) {
}
