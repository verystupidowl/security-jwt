package ru.tggc.securityjwt.dto.request;

import ru.tggc.securityjwt.dto.notification.NotificationType;

public record VerifyRq(String code, String email, NotificationType type) {
}
