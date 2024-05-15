package ru.tggc.securityjwt.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import ru.tggc.securityjwt.model.NoteType;

@Builder
public record NoteDTO(
        long id,
        @NotEmpty(message = "Note name should not be empty!")
        String name,
        NoteType type,
        String text,
        String caption,
        String color
) {
}
