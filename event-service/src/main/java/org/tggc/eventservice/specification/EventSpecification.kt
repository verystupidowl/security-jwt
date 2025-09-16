package org.tggc.eventservice.specification;

import lombok.experimental.UtilityClass;
import org.springframework.data.jpa.domain.Specification;
import org.tggc.eventservice.model.Event;

import java.time.LocalDateTime;

@UtilityClass
public class EventSpecification {

    public static Specification<Event> titleContains(String title) {
        return (root, query, cb) ->
                title == null ? null : cb.like(cb.lower(root.get("title")), "%" + title.toLowerCase() + "%");
    }

    public static Specification<Event> startDateAfter(LocalDateTime date) {
        return (root, query, cb) ->
                date == null ? null : cb.greaterThanOrEqualTo(root.get("startDate"), date);
    }

    public static Specification<Event> endDateBefore(LocalDateTime date) {
        return (root, query, cb) ->
                date == null ? null : cb.lessThanOrEqualTo(root.get("endDate"), date);
    }

    public static Specification<Event> createdBy(Long creatorId) {
        return (root, query, cb) ->
                creatorId == null ? null : cb.equal(root.get("creatorId"), creatorId);
    }
}
