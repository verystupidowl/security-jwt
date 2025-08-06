package org.tggc.notificationservice.dto;

import lombok.Builder;
import org.tggc.notificationapi.dto.NotificationType;

import java.util.List;

@Builder
public record NotificationRq(String to,
                             NotificationType type,
                             String subject,
                             String text,
                             List<ParamDto> params) {
}
