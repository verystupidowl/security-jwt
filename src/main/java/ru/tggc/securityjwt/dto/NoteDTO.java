package ru.tggc.securityjwt.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.tggc.securityjwt.model.NoteType;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NoteDTO {

    private long id;

    private String name;

    private NoteType type;

    private String text;

    private String caption;

    private String color;
}
