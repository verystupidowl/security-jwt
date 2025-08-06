package org.tggc.authenticationservice.dto.request;

import org.tggc.notificationapi.dto.NotificationType;

public record VerifyRq(String code, String email, NotificationType type) {
}
