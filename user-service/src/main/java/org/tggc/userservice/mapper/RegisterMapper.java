package org.tggc.userservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.tggc.authapi.dto.AuthenticationRs;
import org.tggc.authapi.dto.RegisterRq;
import org.tggc.userservice.model.User;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface RegisterMapper extends Mappable<User, RegisterRq, AuthenticationRs> {

    @Mapping(target = "userId", source = "user.id")
    @Override
    AuthenticationRs toDto(User request);
}
