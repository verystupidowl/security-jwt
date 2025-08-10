package org.tggc.eventservice.service;

import org.tggc.eventservice.dto.EventRq;
import org.tggc.eventservice.dto.EventRs;

import java.util.List;

public interface EventService {

    EventRs getEventById(Long eventId);

    List<EventRs> getEventsByUser(Long userId);

    EventRs createEvent(EventRq event, Long userId);

    void joinEvent(Long eventId, Long userId);

    void deleteEvent(Long eventId);
}
