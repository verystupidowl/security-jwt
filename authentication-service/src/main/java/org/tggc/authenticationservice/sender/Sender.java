package org.tggc.authenticationservice.sender;

import org.tggc.notificationapi.dto.NotificationType;

public interface Sender {

    void send(String email, NotificationType type);

    NotificationType getNotificationType();
}
