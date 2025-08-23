package org.tggc.authenticationservice.dto.request;

import lombok.Builder;
import org.tggc.notificationapi.dto.NotificationType;

@Builder
public record VerifyRq(String code, String email, NotificationType notificationType) {
}
