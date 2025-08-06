package org.tggc.authenticationservice.mapper;

import org.mapstruct.Mapper;
import org.tggc.authenticationservice.dto.domain.NoteDto;
import org.tggc.authenticationservice.model.Note;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface NoteMapper extends Mappable<Note, NoteDto> {
}
