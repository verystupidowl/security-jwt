package org.tggc.eventservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tggc.eventservice.dto.EventRq;
import org.tggc.eventservice.dto.EventRs;
import org.tggc.eventservice.exception.EventNotFoundException;
import org.tggc.eventservice.mapper.EventMapper;
import org.tggc.eventservice.model.Event;
import org.tggc.eventservice.model.EventStatus;
import org.tggc.eventservice.model.Participant;
import org.tggc.eventservice.repository.EventRepository;
import org.tggc.eventservice.repository.ParticipantRepository;
import org.tggc.eventservice.service.EventService;
import org.tggc.eventservice.specification.EventSpecification;
import org.tggc.userapi.api.UserApi;
import org.tggc.userapi.dto.UserDto;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;
    private final EventMapper eventMapper;
    private final ParticipantRepository participantRepository;
    private final UserApi userApi;

    @Override
    public EventRs getEventById(Long eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new EventNotFoundException(String.valueOf(eventId)));

        return eventMapper.toEventRs(event);
    }

    @Override
    public List<EventRs> getEventsByUser(Long userId) {
        return eventRepository.findByCreatorId(userId).stream()
                .map(eventMapper::toEventRs)
                .toList();
    }

    @Override
    public EventRs createEvent(EventRq rq, Long userId) {
        Event event = eventMapper.toEvent(rq);
        event.setCreatedAt(LocalDateTime.now());
        event.setCreatorId(userId);
        event.setUpdatedAt(LocalDateTime.now());
        return eventMapper.toEventRs(eventRepository.save(event));
    }

    @Override
    @Transactional
    public void joinEvent(Long eventId, Long userId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new EventNotFoundException(String.valueOf(eventId)));
        event.setUpdatedAt(LocalDateTime.now());

        List<Participant> participants = participantRepository.findByEventId(userId);
        Participant participant = new Participant();
        participant.setUserId(userId);
        participant.setEvent(event);
        participant.setStatus(EventStatus.PENDING);
        participant.setJoinedAd(LocalDateTime.now());
        participants.add(participant);

        eventRepository.save(event);
        participantRepository.saveAll(participants);
    }

    @Override
    public void deleteEvent(Long eventId) {
        eventRepository.deleteById(eventId);
    }

    @Override
    public List<UserDto> getUsersByEvent(Long eventId) {
        List<Long> participantIds = participantRepository.findByEventId(eventId).stream()
                .map(Participant::getUserId)
                .toList();

        return userApi.getUsers(participantIds);
    }

    @Override
    public void leaveEvent(Long eventId, Long userId) {
        List<Participant> participants = participantRepository.findByEventId(eventId);
        participants.removeIf(participant -> userId.equals(participant.getUserId()));
        participantRepository.saveAll(participants);
    }

    @Override
    public List<EventRs> getEventsByFilter(String title,
                                           LocalDateTime startDate,
                                           LocalDateTime endDate,
                                           Long creatorId) {
        Specification<Event> specification = Specification.allOf(
                EventSpecification.titleContains(title),
                EventSpecification.createdBy(creatorId),
                EventSpecification.startDateAfter(startDate),
                EventSpecification.endDateBefore(endDate)
        );

        return eventRepository.findAll(specification).stream()
                .map(eventMapper::toEventRs)
                .toList();
    }
}
