package org.tggc.eventservice.controller

import lombok.RequiredArgsConstructor
import lombok.extern.slf4j.Slf4j
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.tggc.eventservice.aop.Role
import org.tggc.eventservice.aop.annotation.RequiresRoles
import org.tggc.eventservice.dto.EventRq
import org.tggc.eventservice.dto.EventRs
import org.tggc.eventservice.service.EventService
import org.tggc.userapi.dto.UserDto
import java.time.LocalDateTime

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/events")
class EventController(private val eventService: EventService) {

    @GetMapping("/{eventId}")
    fun getEvent(@PathVariable eventId: Long): EventRs {
        return eventService.getEventById(eventId)
    }

    @GetMapping("/by-user")
    fun getEventsByUserId(@RequestHeader("X-User-Id") userId: Long): MutableList<EventRs> {
        return eventService.getEventsByUser(userId)
    }

    @GetMapping("/filter")
    fun getEvents(
        @RequestParam(required = false) title: String?,
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) startDate: LocalDateTime?,
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) endDate: LocalDateTime?,
        @RequestParam(required = false) creatorId: Long?
    ): List<EventRs?> {
        return eventService.getEventsByFilter(title, startDate, endDate, creatorId)
    }

    @GetMapping("/participants/{eventId}")
    fun getParticipantsByEvent(@PathVariable eventId: Long): MutableList<UserDto> {
        return eventService.getUsersByEvent(eventId)
    }

    @PostMapping
    @RequiresRoles(Role.ADMIN, Role.ORGANIZER)
    fun createEvent(@RequestBody event: EventRq, @RequestHeader("X-User-Id") userId: Long): EventRs {
        return eventService.createEvent(event, userId)
    }

    @PostMapping("/join/{eventId}")
    fun joinEvent(@PathVariable eventId: Long, @RequestHeader("X-User-Id") userId: Long) {
        eventService.joinEvent(eventId, userId)
    }

    @PostMapping("/leave/{eventId}")
    fun leaveEvent(@PathVariable eventId: Long, @RequestHeader("X-User-Id") userId: Long) {
        eventService.leaveEvent(eventId, userId)
    }

    @DeleteMapping("/{eventId}")
    @RequiresRoles(Role.ADMIN)
    fun deleteEvent(@PathVariable eventId: Long) {
        eventService.deleteEvent(eventId)
    }
}
