package org.tggc.eventservice.service.impl

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.tggc.eventservice.dto.EventFilter
import org.tggc.eventservice.dto.EventRq
import org.tggc.eventservice.dto.EventRs
import org.tggc.eventservice.exception.AlreadyParticipantException
import org.tggc.eventservice.exception.EventNotFoundException
import org.tggc.eventservice.mapper.toEventEntity
import org.tggc.eventservice.mapper.toRs
import org.tggc.eventservice.model.Event
import org.tggc.eventservice.model.EventStatus
import org.tggc.eventservice.model.Participant
import org.tggc.eventservice.repository.EventRepository
import org.tggc.eventservice.repository.EventRepositorySpec
import org.tggc.eventservice.repository.ParticipantRepository
import org.tggc.eventservice.service.EventService
import org.tggc.userapi.api.UserApi
import org.tggc.userapi.dto.UserDto
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.time.LocalDateTime

@Service
open class EventServiceImpl(
    private val eventRepository: EventRepository,
    private val participantRepository: ParticipantRepository,
    private val eventRepositorySpec: EventRepositorySpec,
    private val userApi: UserApi
) : EventService {

    @Transactional(readOnly = true)
    override fun getEventById(eventId: Long): Mono<EventRs> {
        return eventRepository.findById(eventId)
            .switchIfEmpty(Mono.error(EventNotFoundException(eventId.toString())))
            .map { event: Event -> event.toRs() }
    }

    @Transactional(readOnly = true)
    override fun getEventsByUser(userId: Long): Flux<EventRs> {
        return eventRepository.findByCreatorId(userId)
            .map { event: Event -> event.toRs() }
    }

    @Transactional
    override fun createEvent(
        rq: EventRq,
        userId: Long
    ): Mono<EventRs> {
        val event = rq.toEventEntity()
            .apply {
                this.updatedAt = LocalDateTime.now()
                this.creatorId = userId
                this.createdAt = LocalDateTime.now()
            }
        return eventRepository.save(event)
            .map { it.toRs() }
    }

    @Transactional
    override fun joinEvent(eventId: Long, userId: Long): Mono<Void> {
        return participantRepository.existsByEventIdAndUserId(eventId, userId)
            .flatMap { exists: Boolean ->
                if (exists) {
                    return@flatMap Mono.error<Void>(
                        AlreadyParticipantException(
                            "User with id: $userId is already joined event $eventId"
                        )
                    )
                }


                return@flatMap eventRepository.findById(eventId)
                    .switchIfEmpty(Mono.error(EventNotFoundException(eventId.toString())))
                    .flatMap { event ->
                        event.updatedAt = LocalDateTime.now()

                        val participant = Participant().apply {
                            this.userId = userId
                            this.eventId = event.id
                            this.status = EventStatus.PENDING
                            this.joinedAd = LocalDateTime.now()
                        }

                        Mono.`when`(
                            eventRepository.save(event),
                            participantRepository.save(participant)
                        ).then()
                    }
            }
    }

    @Transactional
    override fun deleteEvent(eventId: Long): Mono<Void> {
        return eventRepository.deleteById(eventId)
            .then()
    }

    @Transactional(readOnly = true)
    override fun getUsersByEvent(eventId: Long): Flux<UserDto> {
        return participantRepository.findByEventId(eventId)
            .map { it.userId!! }
            .collectList()
            .flatMapMany { participantIds ->
                userApi.getUsers(participantIds)
            }
    }

    @Transactional
    override fun leaveEvent(eventId: Long, userId: Long): Mono<Void> {
        return participantRepository.deleteByEventId(eventId)
            .then()
    }

    @Transactional(readOnly = true)
    override fun getEventsByFilter(filter: EventFilter): Flux<EventRs> {
        return eventRepositorySpec.findAll(filter)
            .map { event: Event -> event.toRs() }
    }
}
