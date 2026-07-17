package org.tggc.eventservice.repository

import org.springframework.data.r2dbc.repository.R2dbcRepository
import org.springframework.stereotype.Repository
import org.tggc.eventservice.model.Participant
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Repository
interface ParticipantRepository : R2dbcRepository<Participant, Long> {

    fun findByEventId(eventId: Long?): Flux<Participant>

    fun existsByEventIdAndUserId(eventId: Long, userId: Long): Mono<Boolean>

    fun deleteByEventId(eventId: Long): Mono<Void>
}
