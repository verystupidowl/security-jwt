package org.tggc.userservice.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.tggc.userapi.dto.UserDto;
import org.tggc.userservice.exception.UserBlockedException;
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
    public UserDto getUserById(long userId) {
        return userRepository.findById(userId)
                .map(user -> {
                    if (Boolean.TRUE.equals(user.getBlocked())) {
                        throw new UserBlockedException("User blocked");
                    }
                    return userMapper.userToUserDto(user);
                })
                .orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    @Override
    public List<UserDto> getUsersByIds(List<Long> ids) {
        return userRepository.findAllById(ids).stream()
                .map(userMapper::userToUserDto)
                .toList();
    }

    @Override
    @Transactional
    public void blockUser(Long userId, Boolean block) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("user with id not found " + userId));

        user.setBlocked(block);
        userRepository.save(user);
    }
}
