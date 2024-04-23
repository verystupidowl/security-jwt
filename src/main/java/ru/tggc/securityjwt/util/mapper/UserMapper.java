package ru.tggc.securityjwt.util.mapper;

import org.mapstruct.Mapper;
import ru.tggc.securityjwt.dto.UserDTO;
import ru.tggc.securityjwt.model.User;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface UserMapper extends Mappable<User, UserDTO> {
}
