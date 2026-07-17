package org.tggc.notificationservice.service;

import lombok.NonNull;
import org.tggc.notificationapi.dto.NotificationType;
import org.tggc.notificationservice.dto.NotificationRq;
import reactor.core.publisher.Mono;

public interface SenderService {

    Mono<@NonNull Void> sendNotification(NotificationRq notificationRq);

    NotificationType getNotificationType();
}
