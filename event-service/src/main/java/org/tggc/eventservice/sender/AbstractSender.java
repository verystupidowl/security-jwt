package org.tggc.eventservice.sender;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.tggc.eventservice.model.Event;
import org.tggc.notificationapi.dto.NotificationRq;
import org.tggc.notificationapi.dto.NotificationType;

@RequiredArgsConstructor
public abstract class AbstractSender implements Sender {
    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Override
    public void send(String email, Event event, NotificationType type) {
        NotificationRq notificationRq = getNotificationRqBuilder(email, event)
                .type(type)
                .build();

        kafkaTemplate.send("simple-notification", notificationRq);
    }

    protected abstract NotificationRq.NotificationRqBuilder getNotificationRqBuilder(String email, Event event);
}
