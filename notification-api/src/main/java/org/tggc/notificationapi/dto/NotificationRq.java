package org.tggc.notificationapi.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record NotificationRq(
        String to,
        NotificationType type,
        String subject,
        String text,
        List<ParamDto> params
) {
}

