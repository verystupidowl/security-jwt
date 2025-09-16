package org.tggc.eventservice.mapper;

import org.mapstruct.Mapper;
import org.tggc.eventservice.dto.EventRq;
import org.tggc.eventservice.dto.EventRs;
import org.tggc.eventservice.model.Event;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface EventMapper {

    EventRs toEventRs(Event event);

    Event toEvent(EventRq eventRq);
}
