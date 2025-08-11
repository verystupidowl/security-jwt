package org.tggc.userservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.tggc.userapi.dto.UserDto;
import org.tggc.userservice.exception.UserNotFoundException;
import org.tggc.userservice.mapper.UserMapper;
import org.tggc.userservice.model.User;
import org.tggc.userservice.repository.UserRepository;
import org.tggc.userservice.service.UserService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserDto getUserById(long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        return userMapper.userToUserDto(user);
    }

    @Override
    public List<UserDto> getUsersByIds(List<Long> ids) {
        return userRepository.findAllById(ids).stream()
                .map(userMapper::userToUserDto)
                .toList();
    }
}
