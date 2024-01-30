package ru.tggc.SecurityJWT.util;

import org.springframework.stereotype.Component;
import ru.tggc.SecurityJWT.dto.UserDTO;
import ru.tggc.SecurityJWT.model.User;

@Component
public class UserMapper {

    public User fromUserDtoToUserEntity(UserDTO userDTO) {
        return User.builder()
                .firstname(userDTO.getFirstname())
                .lastname(userDTO.getLastname())
                .email(userDTO.getEmail())
                .build();
    }

    public UserDTO fromUserEntityToUserDTO(User user) {
        return UserDTO.builder()
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .email(user.getEmail())
                .build();
    }
}
