package org.tggc.eventservice.dto;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record EventRs(
        String title,
        String description,
        LocalDateTime eventDate,
        String location,
        LocalDateTime createdAt
) {
}
