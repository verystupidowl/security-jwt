package org.tggc.userservice.mapper;

import org.mapstruct.Mapper;
import org.tggc.userapi.dto.UserDto;
import org.tggc.userservice.model.User;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface UserMapper {

    UserDto userToUserDto(User user);
}
