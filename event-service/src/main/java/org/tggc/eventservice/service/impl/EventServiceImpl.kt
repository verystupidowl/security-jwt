package org.tggc.eventservice.service.impl

import lombok.RequiredArgsConstructor
import org.springframework.data.jpa.domain.Specification
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.tggc.eventservice.dto.EventRq
import org.tggc.eventservice.dto.EventRs
import org.tggc.eventservice.exception.AlreadyParticipantException
import org.tggc.eventservice.exception.EventNotFoundException
import org.tggc.eventservice.mapper.EventMapper
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
@RequiredArgsConstructor
open class EventServiceImpl(
    private val eventRepository: EventRepository,
    private val eventMapper: EventMapper,
    private val participantRepository: ParticipantRepository,
    private val userApi: UserApi
) : EventService {

    override fun getEventById(eventId: Long): EventRs {
        return eventRepository.findById(eventId)
            .map { event -> eventMapper.toEventRs(event) }
            .orElseThrow { EventNotFoundException(eventId.toString()) }
    }

    override fun getEventsByUser(userId: Long): MutableList<EventRs> {
        return eventRepository.findByCreatorId(userId).stream()
            .map { event -> eventMapper.toEventRs(event) }
            .toList()
    }

    override fun createEvent(
        rq: EventRq,
        userId: Long
    ): EventRs {
        val event = eventMapper.toEvent(rq)
            .apply {
                this.updatedAt = LocalDateTime.now()
                this.creatorId = userId
                this.createdAt = LocalDateTime.now()
            }
        return eventMapper.toEventRs(event)
    }

    @Transactional
    override fun joinEvent(eventId: Long, userId: Long) {
        if (participantRepository.existsByEventIdAndUserId(eventId, userId)) {
            throw AlreadyParticipantException("User with id: $userId is already joined event $eventId")
        }

        val event = eventRepository.findById(eventId)
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

    override fun deleteEvent(eventId: Long) {
        eventRepository.deleteById(eventId)
    }

    override fun getUsersByEvent(eventId: Long): MutableList<UserDto> {
        val participantIds = participantRepository.findByEventId(eventId)!!.stream()
            .map { participant -> participant?.userId }
            .toList()

        return userApi.getUsers(participantIds)
    }

    override fun leaveEvent(eventId: Long, userId: Long) {
        val participants = participantRepository.findByEventId(eventId)
        participants?.removeIf { participant -> userId == participant!!.userId }
        participantRepository.saveAll(participants!!)
    }

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

        return eventRepository.findAll(specification).stream()
            .map { event -> eventMapper.toEventRs(event) }
            .toList()
    }
}
