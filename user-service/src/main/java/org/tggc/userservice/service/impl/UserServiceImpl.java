package org.tggc.userservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tggc.userapi.dto.UserDto;
import org.tggc.userservice.exception.UserNotFoundException;
import org.tggc.userservice.mapper.UserMapper;
import org.tggc.userservice.repository.UserRepository;
import org.tggc.userservice.service.UserService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    @Transactional(readOnly = true)
    public Mono<UserDto> getUserById(long userId) {
        return userRepository.findById(userId)
                .flatMap(user -> Mono.just(userMapper.toDto(user)))
                .cast(UserDto.class)
                .switchIfEmpty(Mono.error(new UserNotFoundException("User not found")));
    }

    @Override
    @Transactional(readOnly = true)
    public Flux<UserDto> getUsersByIds(List<Long> ids) {
        return userRepository.findAllById(ids)
                .map(userMapper::toDto);
    }

    @Override
    public Mono<UserDto> getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(userMapper::toDto);
    }
}
