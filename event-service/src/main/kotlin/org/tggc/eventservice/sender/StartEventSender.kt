package org.tggc.eventservice.sender

import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service
import org.tggc.eventservice.model.Event
import org.tggc.notificationapi.dto.NotificationRq
import org.tggc.notificationapi.dto.NotificationType

@Service
class StartEventSender(kafkaTemplate: KafkaTemplate<String, Any>) : AbstractSender(kafkaTemplate) {

    override fun getNotificationRqBuilder(event: Event?): NotificationRq.NotificationRqBuilder? {
        val text = "Начинается событие: " + event?.title
        val subject = "Начало события"
        return NotificationRq.builder()
            .text(text)
            .subject(subject)
    }

    override val notificationType: NotificationType
        get() = NotificationType.START_EVENT
}
