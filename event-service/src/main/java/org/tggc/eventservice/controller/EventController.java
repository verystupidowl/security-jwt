package org.tggc.eventservice.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.tggc.eventservice.aop.Role;
import org.tggc.eventservice.aop.annotation.RequiresRoles;
import org.tggc.eventservice.dto.EventRq;
import org.tggc.eventservice.dto.EventRs;
import org.tggc.eventservice.service.EventService;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
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

    @PostMapping
    @RequiresRoles({Role.ADMIN, Role.ORGANIZER})
    public EventRs createEvent(@RequestBody EventRq event, @RequestHeader("X-User-Id") Long userId) {
        return eventService.createEvent(event, userId);
    }

    @PostMapping("/join{eventId}")
    public void joinEvent(@PathVariable Long eventId, @RequestHeader("X-User-Id") Long userId) {
        eventService.joinEvent(eventId, userId);
    }

    @DeleteMapping("{eventId}")
    @RequiresRoles({Role.ADMIN})
    public void deleteEvent(@PathVariable Long eventId) {
        eventService.deleteEvent(eventId);
    }
}
