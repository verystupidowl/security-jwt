package org.tggc.authenticationservice.mapper;

import org.mapstruct.Mapper;
import org.tggc.authenticationservice.dto.domain.UserDto;
import org.tggc.authenticationservice.model.User;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface UserMapper extends Mappable<User, UserDto> {
}
