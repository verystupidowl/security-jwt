package org.tggc.eventservice.controller

import lombok.RequiredArgsConstructor
import lombok.extern.slf4j.Slf4j
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.tggc.eventservice.aop.Role
import org.tggc.eventservice.aop.annotation.RequiresRoles
import org.tggc.eventservice.dto.EventFilter
import org.tggc.eventservice.dto.EventRq
import org.tggc.eventservice.dto.EventRs
import org.tggc.eventservice.service.EventService
import org.tggc.userapi.dto.UserDto
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/events")
class EventController(private val eventService: EventService) {
    @GetMapping("/{eventId}")
    fun getEvent(@PathVariable eventId: Long): Mono<EventRs> = eventService.getEventById(eventId)


    @GetMapping("/by-user")
    fun getEventsByUserId(@RequestHeader("X-User-Id") userId: Long): Flux<EventRs> =
        eventService.getEventsByUser(userId)


    @GetMapping("/filter")
    fun getEvents(filter: EventFilter): Flux<EventRs> = eventService.getEventsByFilter(filter)


    @GetMapping("/participants/{eventId}")
    fun getParticipantsByEvent(@PathVariable eventId: Long): Flux<UserDto> = eventService.getUsersByEvent(eventId)


    @PostMapping
    @RequiresRoles(Role.ADMIN, Role.ORGANIZER)
    fun createEvent(@RequestBody event: EventRq, @RequestHeader("X-User-Id") userId: Long): Mono<EventRs> =
        eventService.createEvent(event, userId)


    @PostMapping("/join/{eventId}")
    fun joinEvent(@PathVariable eventId: Long, @RequestHeader("X-User-Id") userId: Long): Mono<Void> =
        eventService.joinEvent(eventId, userId)


    @PostMapping("/leave/{eventId}")
    fun leaveEvent(@PathVariable eventId: Long, @RequestHeader("X-User-Id") userId: Long): Mono<Void> =
        eventService.leaveEvent(eventId, userId)


    @DeleteMapping("/{eventId}")
    @RequiresRoles(Role.ADMIN)
    fun deleteEvent(@PathVariable eventId: Long): Mono<Void> = eventService.deleteEvent(eventId)

}
