package org.tggc.eventservice.sender

import org.tggc.eventservice.model.Event
import org.tggc.notificationapi.dto.NotificationType
import reactor.core.publisher.Mono

interface Sender {

    fun send(email: String, event: Event, type: NotificationType): Mono<Void>

    val notificationType: NotificationType
}
