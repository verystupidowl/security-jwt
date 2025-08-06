package org.tggc.authenticationservice.dto.domain;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import org.tggc.authenticationservice.model.NoteType;

@Builder
public record NoteDto(
        long id,
        @NotEmpty(message = "Note name should not be empty!")
        String name,
        NoteType type,
        String text,
        String caption,
        String color
) {
}
