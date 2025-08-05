package ru.tggc.securityjwt.sender;

import ru.tggc.securityjwt.dto.notification.NotificationType;

public interface Sender {

    void send(String email, NotificationType type);

    NotificationType getNotificationType();
}
