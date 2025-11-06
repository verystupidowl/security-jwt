package org.tggc.authenticationservice.sender.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.tggc.authenticationservice.sender.Sender;
import org.tggc.notificationapi.dto.NotificationRq;
import org.tggc.notificationapi.dto.NotificationType;
import reactor.core.publisher.Mono;
import reactor.kafka.sender.KafkaSender;
import reactor.kafka.sender.SenderRecord;

@Slf4j
@Service
@RequiredArgsConstructor
public abstract class AbstractSender implements Sender {
    @Value("${spring.kafka-topic.simple-notification}")
    private String topic;
    private final KafkaSender<String, Object> kafkaSender;

    @Override
    public Mono<Void> send(String email, NotificationType type) {
        var notificationDto = getNotificationDtoBuilder()
                .to(email)
                .type(type)
                .build();

        log.info("sending email: {}", notificationDto.toString());

        SenderRecord<String, Object, String> senderRecord = SenderRecord.create(
                new ProducerRecord<>(topic, "simple-notification", notificationDto),
                email
        );

        return kafkaSender
                .send(Mono.just(senderRecord))
                .doOnError(Mono::error)
                .doOnNext(result ->
                        log.info("Code has been sent to topic: {}, offset: {}, email: {}",
                                topic,
                                result.recordMetadata().offset(),
                                result.correlationMetadata()))
                .then();
    }

    protected abstract NotificationRq.NotificationRqBuilder getNotificationDtoBuilder();
}
