package org.tggc.eventservice.scheduler;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.tggc.eventservice.model.Event;
import org.tggc.eventservice.repository.EventRepository;
import org.tggc.eventservice.sender.StartEventSender;
import org.tggc.notificationapi.dto.NotificationType;
import org.tggc.userapi.api.UserApi;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class EventStartScheduler {
    private final EventRepository eventRepository;
    private final StartEventSender startEventSender;
    private final UserApi userApi;

    @Scheduled(fixedRate = 3600000)
    public void checkEventsStartingSoon() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime soon = now.plusHours(1);

        List<Event> eventsStartingSoon = eventRepository.findByEventDateBetween(now, soon);

        for (Event event : eventsStartingSoon) {
            String to = userApi.getUserById(event.getCreatorId()).email();

            startEventSender.send(to, event, NotificationType.START_EVENT);
        }
    }
}
