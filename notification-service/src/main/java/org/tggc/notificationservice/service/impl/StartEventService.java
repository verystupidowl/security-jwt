package org.tggc.notificationservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.tggc.notificationapi.dto.NotificationType;
import org.tggc.notificationservice.dto.NotificationRq;
import org.tggc.notificationservice.service.MailSender;
import org.tggc.notificationservice.service.SenderService;

@Service
@RequiredArgsConstructor
public class StartEventService implements SenderService {
    private final MailSender mailSender;

    @Override
    public void sendNotification(NotificationRq notificationRq) {
        mailSender.send(notificationRq);
    }

    @Override
    public NotificationType getNotificationType() {
        return NotificationType.START_EVENT;
    }
}
