package org.tggc.notificationservice.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.tggc.notificationservice.dto.NotificationRq;
import org.tggc.notificationservice.service.SenderService;
import org.tggc.notificationservice.service.factory.SenderFactory;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationListener {
    private final SenderFactory factory;
    private final ObjectMapper objectMapper;

    @KafkaListener(
            topics = "${spring.kafka-topic.simple-notification}",
            groupId = "${spring.kafka.consumer.group-id}",
            containerFactory = "listenerContainerFactory"
    )
    @SneakyThrows
    public void listen(String message) {
        NotificationRq notificationRq = objectMapper.readValue(message, NotificationRq.class);
        log.info("Received change password message: {}", notificationRq);
        SenderService service = factory.getSenderService(notificationRq.type());
        service.sendNotification(notificationRq);
    }
}
