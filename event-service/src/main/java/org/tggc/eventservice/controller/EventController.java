package org.tggc.eventservice.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.tggc.eventservice.aop.Role;
import org.tggc.eventservice.aop.annotation.RequiresRoles;
import org.tggc.eventservice.dto.EventRq;
import org.tggc.eventservice.dto.EventRs;
import org.tggc.eventservice.service.EventService;
import org.tggc.userapi.dto.UserDto;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/events")
public class EventController {
    private final EventService eventService;

    @GetMapping("/{eventId}")
    public EventRs getEvent(@PathVariable Long eventId) {
        return eventService.getEventById(eventId);
    }

    @GetMapping("/by-user")
    public List<EventRs> getEventsByUserId(@RequestHeader("X-User-Id") Long userId) {
        return eventService.getEventsByUser(userId);
    }

    @GetMapping("/filter")
    public List<EventRs> getEvents(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
            @RequestParam(required = false) Long creatorId) {
        return eventService.getEventsByFilter(title, startDate, endDate, creatorId);
    }

    @GetMapping("/participants/{eventId}")
    public List<UserDto> getParticipantsByEvent(@PathVariable Long eventId) {
        return eventService.getUsersByEvent(eventId);
    }

    @PostMapping
    @RequiresRoles({Role.ADMIN, Role.ORGANIZER})
    public EventRs createEvent(@RequestBody EventRq event, @RequestHeader("X-User-Id") Long userId) {
        return eventService.createEvent(event, userId);
    }

    @PostMapping("/join/{eventId}")
    public void joinEvent(@PathVariable Long eventId, @RequestHeader("X-User-Id") Long userId) {
        eventService.joinEvent(eventId, userId);
    }

    @PostMapping("/leave/{eventId}")
    public void leaveEvent(@PathVariable Long eventId, @RequestHeader("X-User-Id") Long userId) {
        eventService.leaveEvent(eventId, userId);
    }

    @DeleteMapping("/{eventId}")
    @RequiresRoles({Role.ADMIN})
    public void deleteEvent(@PathVariable Long eventId) {
        eventService.deleteEvent(eventId);
    }
}
