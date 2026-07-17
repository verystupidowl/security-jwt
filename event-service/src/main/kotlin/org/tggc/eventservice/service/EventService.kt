package org.tggc.eventservice.service

import org.tggc.eventservice.dto.EventFilter
import org.tggc.eventservice.dto.EventRq
import org.tggc.eventservice.dto.EventRs
import org.tggc.userapi.dto.UserDto
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface EventService {

    fun getEventById(eventId: Long): Mono<EventRs>

    fun getEventsByUser(userId: Long): Flux<EventRs>

    fun createEvent(rq: EventRq, userId: Long): Mono<EventRs>

    fun joinEvent(eventId: Long, userId: Long): Mono<Void>

    fun deleteEvent(eventId: Long): Mono<Void>

    fun getUsersByEvent(eventId: Long): Flux<UserDto>

    fun leaveEvent(eventId: Long, userId: Long): Mono<Void>

    fun getEventsByFilter(filter: EventFilter): Flux<EventRs>
}
