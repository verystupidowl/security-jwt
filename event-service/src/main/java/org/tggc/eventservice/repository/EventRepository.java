package org.tggc.eventservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.tggc.eventservice.model.Event;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

    List<Event> findByCreatorId(Long userId);

    List<Event> findByEventDateBetween(LocalDateTime now, LocalDateTime soon);
}
