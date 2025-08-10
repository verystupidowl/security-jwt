package org.tggc.eventservice.sender;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.tggc.eventservice.model.Event;
import org.tggc.notificationapi.dto.NotificationRq;
import org.tggc.notificationapi.dto.NotificationType;

@Service
@RequiredArgsConstructor
public class StartEventSender {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    @SneakyThrows
    public void sendStartEvent(Event event) {
        String text = "Начинается событие: " + event.getTitle();
        String subject = "Начало события";

        NotificationRq rq = new NotificationRq(
                event.getCreatorId().toString(),
                NotificationType.START_EVENT,
                subject,
                text,
                null
        );
        String json = objectMapper.writeValueAsString(rq);
        kafkaTemplate.send("simple-notification", json);
    }
}
