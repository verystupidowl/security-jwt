package org.tggc.authenticationservice.mapper;

import org.mapstruct.Mapper;
import org.tggc.authenticationservice.dto.domain.UserRs;
import org.tggc.authenticationservice.model.User;
import org.tggc.userapi.dto.UserDto;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface UserMapper extends Mappable<User, UserRs> {

    UserDto toApiDto(User user);
}
