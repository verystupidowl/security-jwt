package org.tggc.authapi.dto;

import java.util.List;

public record AuthenticationRs(String email, List<String> roles, Long userId) {
}
