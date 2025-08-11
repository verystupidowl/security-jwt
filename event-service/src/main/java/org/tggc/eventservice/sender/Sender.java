package org.tggc.eventservice.sender;

import org.tggc.eventservice.model.Event;
import org.tggc.notificationapi.dto.NotificationType;

public interface Sender {

    void send(String email, Event event, NotificationType type);

    NotificationType getNotificationType();
}
