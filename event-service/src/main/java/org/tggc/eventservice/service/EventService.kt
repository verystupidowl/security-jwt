package org.tggc.eventservice.service

import org.tggc.eventservice.dto.EventRq
import org.tggc.eventservice.dto.EventRs
import org.tggc.userapi.dto.UserDto
import java.time.LocalDateTime

interface EventService {
    fun getEventById(eventId: Long): EventRs

    fun getEventsByUser(userId: Long): MutableList<EventRs>

    fun createEvent(rq: EventRq, userId: Long): EventRs

    fun joinEvent(eventId: Long, userId: Long)

    fun deleteEvent(eventId: Long)

    fun getUsersByEvent(eventId: Long): MutableList<UserDto>

    fun leaveEvent(eventId: Long, userId: Long)

    fun getEventsByFilter(
        title: String?,
        startDate: LocalDateTime?,
        endDate: LocalDateTime?,
        creatorId: Long?
    ): List<EventRs?>
}
