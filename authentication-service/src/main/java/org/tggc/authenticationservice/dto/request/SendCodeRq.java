package org.tggc.authenticationservice.dto.request;

import jakarta.validation.constraints.Email;
import org.tggc.notificationapi.dto.NotificationType;

public record SendCodeRq(@Email String email, NotificationType type) {
}
