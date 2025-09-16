package org.tggc.eventservice.mapper

import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.MappingConstants
import org.tggc.eventservice.dto.EventRq
import org.tggc.eventservice.dto.EventRs
import org.tggc.eventservice.model.Event

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
interface EventMapper {
    @Mapping(target = "participants", source = "participants.name")
    fun toEventRs(event: Event?): EventRs

    fun toEvent(eventRq: EventRq?): Event
}
