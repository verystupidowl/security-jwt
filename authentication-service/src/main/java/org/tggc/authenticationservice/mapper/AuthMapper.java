package org.tggc.authenticationservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.tggc.authapi.dto.AuthenticationRs;
import org.tggc.authenticationservice.model.User;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface AuthMapper extends Mappable<User, AuthenticationRs> {

    @Override
    @Mapping(target = "roles", source = "java(List.of(user.getRole()))")
    @Mapping(target = "userId", source = "id")
    AuthenticationRs toDto(User user);
}
