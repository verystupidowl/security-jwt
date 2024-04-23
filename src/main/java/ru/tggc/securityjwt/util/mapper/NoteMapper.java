package ru.tggc.securityjwt.util.mapper;

import org.mapstruct.Mapper;
import ru.tggc.securityjwt.dto.NoteDTO;
import ru.tggc.securityjwt.model.Note;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface NoteMapper extends Mappable<Note, NoteDTO> {
}
