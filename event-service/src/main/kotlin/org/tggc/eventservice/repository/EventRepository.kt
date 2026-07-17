package org.tggc.eventservice.repository

import org.springframework.data.r2dbc.repository.R2dbcRepository
import org.springframework.stereotype.Repository
import org.tggc.eventservice.model.Event
import reactor.core.publisher.Flux
import java.time.LocalDateTime

@Repository
interface EventRepository : R2dbcRepository<Event, Long> {

    fun findByCreatorId(userId: Long): Flux<Event>

    fun findByEventDateBetween(now: LocalDateTime, soon: LocalDateTime): Flux<Event>
}
