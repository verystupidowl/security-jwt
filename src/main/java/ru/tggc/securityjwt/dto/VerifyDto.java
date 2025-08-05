package ru.tggc.securityjwt.dto;

import ru.tggc.securityjwt.dto.notification.NotificationType;

public record VerifyDto(String code, String email, NotificationType type) {
}
