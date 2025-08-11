package org.tggc.authenticationservice.sender.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.tggc.authenticationservice.sender.Sender;
import org.tggc.notificationapi.dto.NotificationRq;
import org.tggc.notificationapi.dto.NotificationType;

@Slf4j
@Service
@RequiredArgsConstructor
public abstract class AbstractSender implements Sender {
    @Value("${spring.kafka-topic.simple-notification}")
    private String topic;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Override
    public void send(String email, NotificationType type) {
        NotificationRq notificationDto = getNotificationDtoBuilder()
                .to(email)
                .type(type)
                .build();
        kafkaTemplate.send(topic, "simple-notification", notificationDto);
        log.info("Result sent to topic: {}", topic);
    }

    protected abstract NotificationRq.NotificationRqBuilder getNotificationDtoBuilder();
}
