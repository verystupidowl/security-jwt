package org.tggc.eventservice.scheduler;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.tggc.eventservice.model.Event;
import org.tggc.eventservice.repository.EventRepository;
import org.tggc.eventservice.sender.StartEventSender;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class EventStartScheduler {
    private final EventRepository eventRepository;
    private final StartEventSender startEventSender;

    @Scheduled(fixedRate = 3600000)
    public void checkEventsStartingSoon() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime soon = now.plusMinutes(1);

        List<Event> eventsStartingSoon = eventRepository.findByEventDateBetween(now, soon);

        for (Event event : eventsStartingSoon) {
            startEventSender.sendStartEvent(event);
        }
    }
}
