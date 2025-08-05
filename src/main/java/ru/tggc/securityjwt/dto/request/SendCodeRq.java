package ru.tggc.securityjwt.dto.request;

import jakarta.validation.constraints.Email;
import ru.tggc.securityjwt.dto.notification.NotificationType;

public record SendCodeRq(@Email String email, NotificationType type) {
}
