package org.tggc.authenticationservice.dto.notification;

import lombok.Builder;
import org.tggc.notificationapi.dto.NotificationType;

import java.util.List;

@Builder
public record NotificationDto(String to,
                              NotificationType type,
                              String subject,
                              String text,
                              List<ParamDto> params) {
}
