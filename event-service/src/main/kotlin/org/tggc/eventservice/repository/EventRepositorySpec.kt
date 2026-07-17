package org.tggc.eventservice.repository

import org.tggc.eventservice.dto.EventFilter
import org.tggc.eventservice.model.Event
import reactor.core.publisher.Flux

interface EventRepositorySpec {
    fun findAll(filter: EventFilter): Flux<Event>
}
