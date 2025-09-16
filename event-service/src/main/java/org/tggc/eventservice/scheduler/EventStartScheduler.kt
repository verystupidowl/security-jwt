package org.tggc.eventservice.scheduler

import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import org.tggc.eventservice.model.Event
import org.tggc.eventservice.repository.EventRepository
import org.tggc.eventservice.sender.StartEventSender
import org.tggc.notificationapi.dto.NotificationType
import org.tggc.userapi.api.UserApi
import java.time.LocalDateTime

@Component
class EventStartScheduler(
    private val eventRepository: EventRepository,
    private val startEventSender: StartEventSender,
    private val userApi: UserApi
) {
    @Scheduled(fixedRate = 3600000)
    fun checkEventsStartingSoon() {
        val now = LocalDateTime.now()
        val soon = now.plusHours(1)

        val eventsStartingSoon: MutableList<Event> = eventRepository.findByEventDateBetween(now, soon)

        eventsStartingSoon.forEach { event ->
            val to = event.creatorId?.let { userApi.getUserById(it) }?.email

            startEventSender.send(to, event, NotificationType.START_EVENT)
        }
    }
}
