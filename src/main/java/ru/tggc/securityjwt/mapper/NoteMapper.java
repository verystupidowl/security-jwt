package ru.tggc.securityjwt.mapper;

import org.mapstruct.Mapper;
import ru.tggc.securityjwt.dto.NoteDto;
import ru.tggc.securityjwt.model.Note;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface NoteMapper extends Mappable<Note, NoteDto> {
}
