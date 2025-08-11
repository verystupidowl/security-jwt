package org.tggc.eventservice.dto;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record EventRq(
        String title,
        String description,
        LocalDateTime eventDate,
        String location
) {
}
