package ru.tggc.securityjwt.mapper;

import org.mapstruct.Mapper;
import ru.tggc.securityjwt.dto.domain.UserDto;
import ru.tggc.securityjwt.model.User;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface UserMapper extends Mappable<User, UserDto> {
}
