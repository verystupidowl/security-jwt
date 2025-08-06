package org.tggc.notificationservice.service;

import org.tggc.notificationapi.dto.NotificationType;
import org.tggc.notificationservice.dto.NotificationRq;

public interface SenderService {

    void sendNotification(NotificationRq notificationRq);

    NotificationType getNotificationType();
}
