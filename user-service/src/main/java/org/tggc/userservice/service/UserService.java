package org.tggc.userservice.service;

import org.tggc.userapi.dto.UserDto;

import java.util.List;

public interface UserService {

    UserDto getUserById(long id);

    List<UserDto> getUsersByIds(List<Long> ids);

    void blockUser(Long userId, Boolean block);
}
