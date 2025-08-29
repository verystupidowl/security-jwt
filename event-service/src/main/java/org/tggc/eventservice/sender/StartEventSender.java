package org.tggc.eventservice.sender;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.tggc.eventservice.model.Event;
import org.tggc.notificationapi.dto.NotificationRq;
import org.tggc.notificationapi.dto.NotificationType;

@Service
public class StartEventSender extends AbstractSender {

    public StartEventSender(KafkaTemplate<String, Object> kafkaTemplate) {
        super(kafkaTemplate);
    }

    @Override
    public NotificationType getNotificationType() {
        return null;
    }

    @Override
    protected NotificationRq.NotificationRqBuilder getNotificationRqBuilder(Event event) {
        String text = "Начинается событие: " + event.getTitle();
        String subject = "Начало события";
        return NotificationRq.builder()
                .text(text)
                .subject(subject);
    }
}
