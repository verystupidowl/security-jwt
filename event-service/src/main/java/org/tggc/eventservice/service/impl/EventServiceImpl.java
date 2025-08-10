package org.tggc.eventservice.service.impl;

import lombok.RequiredArgsConstructor;
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

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;
    private final EventMapper eventMapper;
    private final ParticipantRepository participantRepository;

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
        eventRepository.save(event);

        Participant participant = participantRepository.findByEventId(userId)
                .orElseGet(() -> {
                    Participant newParticipant = new Participant();
                    newParticipant.setUserId(userId);
                    newParticipant.setEvent(event);
                    newParticipant.setStatus(EventStatus.PENDING);
                    return newParticipant;
                });
        participant.setJoinedAd(LocalDateTime.now());

        participantRepository.save(participant);
    }

    @Override
    public void deleteEvent(Long eventId) {
        eventRepository.deleteById(eventId);
    }
}
