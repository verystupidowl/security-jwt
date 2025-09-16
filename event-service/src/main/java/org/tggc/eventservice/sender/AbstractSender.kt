package org.tggc.eventservice.sender

import lombok.RequiredArgsConstructor
import org.springframework.kafka.core.KafkaTemplate
import org.tggc.eventservice.model.Event
import org.tggc.notificationapi.dto.NotificationRq
import org.tggc.notificationapi.dto.NotificationType

@RequiredArgsConstructor
abstract class AbstractSender(private val kafkaTemplate: KafkaTemplate<String, Any>) : Sender {

    override fun send(email: String?, event: Event?, type: NotificationType?) {
        val notificationRq = getNotificationRqBuilder(event)!!
            .type(type)
            .to(email)
            .build()

        kafkaTemplate.send("simple-notification", notificationRq)
    }

    protected abstract fun getNotificationRqBuilder(event: Event?): NotificationRq.NotificationRqBuilder?
}
