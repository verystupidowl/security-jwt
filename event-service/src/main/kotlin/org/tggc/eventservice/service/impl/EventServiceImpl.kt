package org.tggc.eventservice.service.impl

import org.springframework.data.jpa.domain.Specification
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
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
import org.tggc.eventservice.repository.ParticipantRepository
import org.tggc.eventservice.service.EventService
import org.tggc.eventservice.specification.EventSpecification
import org.tggc.userapi.api.UserApi
import org.tggc.userapi.dto.UserDto
import java.time.LocalDateTime

@Service
open class EventServiceImpl(
    private val eventRepository: EventRepository,
    private val participantRepository: ParticipantRepository,
    private val userApi: UserApi
) : EventService {

    @Transactional(readOnly = true)
    override fun getEventById(eventId: Long): EventRs {
        return eventRepository.findById(eventId)
            .map { event: Event -> event.toRs() }
            .orElseThrow { EventNotFoundException(eventId.toString()) }
    }

    @Transactional(readOnly = true)
    override fun getEventsByUser(userId: Long): List<EventRs> {
        return eventRepository.findByCreatorId(userId)
            .map { event: Event -> event.toRs() }
    }

    @Transactional
    override fun createEvent(
        rq: EventRq,
        userId: Long
    ): EventRs {
        return rq.toEventEntity()
            .apply {
                this.updatedAt = LocalDateTime.now()
                this.creatorId = userId
                this.createdAt = LocalDateTime.now()
            }
            .toRs()
    }

    @Transactional
    override fun joinEvent(eventId: Long, userId: Long) {
        if (participantRepository.existsByEventIdAndUserId(eventId, userId)) {
            throw AlreadyParticipantException("User with id: $userId is already joined event $eventId")
        }

        val event: Event? = eventRepository.findById(eventId)
            .orElseThrow { EventNotFoundException(eventId.toString()) }
        event?.updatedAt = LocalDateTime.now()

        val participant = Participant().apply {
            this.userId = userId
            this.event = event
            this.status = EventStatus.PENDING
            this.joinedAd = LocalDateTime.now()
        }

        eventRepository.save(event!!)
        participantRepository.save(participant)
    }

    @Transactional
    override fun deleteEvent(eventId: Long) {
        eventRepository.deleteById(eventId)
    }

    @Transactional(readOnly = true)
    override fun getUsersByEvent(eventId: Long): List<UserDto> {
        val participantIds = participantRepository.findByEventId(eventId)
            .map { participant -> participant.userId }

        return userApi.getUsers(participantIds)
            .toStream()
            .toList()
    }

    @Transactional
    override fun leaveEvent(eventId: Long, userId: Long) {
        val participants = participantRepository.findByEventId(eventId)
        participants.removeIf { participant -> userId == participant.userId }
        participantRepository.saveAll(participants)
    }

    @Transactional(readOnly = true)
    override fun getEventsByFilter(
        title: String?,
        startDate: LocalDateTime?,
        endDate: LocalDateTime?,
        creatorId: Long?
    ): List<EventRs> {
        val specification = Specification.allOf(
            EventSpecification.titleContains(title),
            EventSpecification.createdBy(creatorId),
            EventSpecification.startDateAfter(startDate),
            EventSpecification.endDateBefore(endDate)
        )

        return eventRepository.findAll(specification)
            .map { event: Event -> event.toRs() }
    }
}
