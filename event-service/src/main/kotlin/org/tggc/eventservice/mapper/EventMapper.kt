package org.tggc.eventservice.mapper

import org.tggc.eventservice.dto.EventRq
import org.tggc.eventservice.dto.EventRs
import org.tggc.eventservice.model.Event

fun Event.toRs(): EventRs = EventRs(
    id = this.id,
    title = this.title,
    description = this.description,
    eventDate = this.eventDate,
    location = this.location,
    createdAt = this.createdAt,
    participants = this.participants
)

fun EventRq.toEventEntity(): Event = Event(
    title = this.title,
    description = this.description,
    eventDate = this.eventDate,
    location = this.location,
)