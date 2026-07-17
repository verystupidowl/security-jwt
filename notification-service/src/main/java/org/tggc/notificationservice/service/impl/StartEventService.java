package org.tggc.notificationservice.service.impl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.tggc.notificationapi.dto.NotificationType;
import org.tggc.notificationservice.dto.NotificationRq;
import org.tggc.notificationservice.service.MailSender;
import org.tggc.notificationservice.service.SenderService;
import reactor.core.publisher.Mono;

import static org.tggc.notificationapi.dto.NotificationType.START_EVENT;

@Service
@RequiredArgsConstructor
public class StartEventService implements SenderService {
    private final MailSender mailSender;

    @Override
    public Mono<@NonNull Void> sendNotification(NotificationRq notificationRq) {
        return mailSender.send(notificationRq);
    }

    @Override
    public NotificationType getNotificationType() {
        return START_EVENT;
    }
}
