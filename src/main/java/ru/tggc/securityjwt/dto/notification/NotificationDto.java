package ru.tggc.securityjwt.dto.notification;

import lombok.Builder;

import java.util.List;

@Builder
public record NotificationDto(String to,
                              NotificationType type,
                              String subject,
                              String text,
                              List<ParamDto> params) {
}
