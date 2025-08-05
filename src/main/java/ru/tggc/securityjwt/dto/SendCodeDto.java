package ru.tggc.securityjwt.dto;

import jakarta.validation.constraints.Email;
import ru.tggc.securityjwt.dto.notification.NotificationType;

public record SendCodeDto(@Email String email, NotificationType type) {
}
