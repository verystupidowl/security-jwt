package org.tggc.eventservice.sender

import org.tggc.eventservice.model.Event
import org.tggc.notificationapi.dto.NotificationType

interface Sender {

    fun send(email: String?, event: Event?, type: NotificationType?)

    val notificationType: NotificationType?
}
