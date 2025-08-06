package org.tggc.notificationservice.service;

import org.tggc.notificationservice.dto.NotificationRq;

public interface MailSender {

    void send(NotificationRq notificationRq);
}
