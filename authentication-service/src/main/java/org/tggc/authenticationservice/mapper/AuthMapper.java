package org.tggc.authenticationservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.tggc.authapi.dto.AuthenticationRs;
import org.tggc.authenticationservice.model.UserCredentials;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface AuthMapper extends Mappable<UserCredentials, AuthenticationRs> {

    @Override
    @Mapping(target = "roles", expression = "java(List.of(userCredentials.getRole().name()))")
    @Mapping(target = "userId", source = "id")
    AuthenticationRs toDto(UserCredentials userCredentials);
}
