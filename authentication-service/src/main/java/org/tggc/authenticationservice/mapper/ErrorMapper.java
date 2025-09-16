package org.tggc.authenticationservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.tggc.authapi.dto.ErrorRs;

import java.util.List;
import java.util.stream.Collectors;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;
import static org.mapstruct.ReportingPolicy.IGNORE;

@Mapper(componentModel = SPRING, unmappedSourcePolicy = IGNORE)
public interface ErrorMapper extends Mappable<MethodArgumentNotValidException, ErrorRs> {

    @Mapping(target = "status", expression = "java(org.springframework.http.HttpStatus.BAD_REQUEST)")
    @Mapping(target = "msg", source = "e.fieldErrors", qualifiedByName = "mapFieldErrorsToMessage")
    @Mapping(target = "timestamp", expression = "java(LocalDateTime.now())")
    @Override
    ErrorRs toDto(MethodArgumentNotValidException e);

    @Named("mapFieldErrorsToMessage")
    default String mapFieldErrorsToMessage(List<FieldError> fieldErrors) {
        return fieldErrors.stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.joining(", "));
    }
}
