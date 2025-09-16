package org.tggc.eventservice.service;

import org.tggc.eventservice.dto.EventRq;
import org.tggc.eventservice.dto.EventRs;
import org.tggc.userapi.dto.UserDto;

import java.time.LocalDateTime;
import java.util.List;

public interface EventService {

    EventRs getEventById(Long eventId);

    List<EventRs> getEventsByUser(Long userId);

    EventRs createEvent(EventRq event, Long userId);

    void joinEvent(Long eventId, Long userId);

    void deleteEvent(Long eventId);

    List<UserDto> getUsersByEvent(Long eventId);

    void leaveEvent(Long eventId, Long userId);

    List<EventRs> getEventsByFilter(String title,
                                    LocalDateTime startDate,
                                    LocalDateTime endDate,
                                    Long creatorId);
}
