package org.tggc.eventservice.sender

import lombok.RequiredArgsConstructor
import org.apache.kafka.clients.producer.ProducerRecord
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.tggc.eventservice.model.Event
import org.tggc.notificationapi.dto.NotificationRq
import org.tggc.notificationapi.dto.NotificationType
import reactor.core.publisher.Mono
import reactor.kafka.sender.KafkaSender
import reactor.kafka.sender.SenderRecord

@RequiredArgsConstructor
abstract class AbstractSender(
    private val kafkaSender: KafkaSender<String, Any>,
    @param:Value($$"${spring.kafka-topic.simple-notification}") private val topic: String
) : Sender {
    private val log = LoggerFactory.getLogger(this.javaClass)

    override fun send(email: String, event: Event, type: NotificationType): Mono<Void> {
        val notificationRq = getNotificationRqBuilder(event)
            .type(type)
            .to(email)
            .build()

        log.info("Sending event: $event")

        val record: SenderRecord<String, Any, String> = SenderRecord.create(
            ProducerRecord(topic, "simple-notification", notificationRq),
            email
        )

        return kafkaSender.send(Mono.just(record))
            .doOnError { e -> log.error("Error sending event", e) }
            .doOnNext { log.info("send event: $it") }
            .then()
    }

    protected abstract fun getNotificationRqBuilder(event: Event): NotificationRq.NotificationRqBuilder
}
