package org.tggc.userservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.tggc.userapi.dto.UserDto;
import org.tggc.userservice.model.User;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface UserMapper extends Mappable<User,  UserDto> {

    @Mapping(target = "role", expression = "java(user.getRole().name())")
    @Override
    UserDto toDto(User user);
}
