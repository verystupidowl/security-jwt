package ru.tggc.SecurityJWT.util;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import ru.tggc.SecurityJWT.dto.UserDTO;
import ru.tggc.SecurityJWT.model.User;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper extends Mappable<User, UserDTO> {
}
