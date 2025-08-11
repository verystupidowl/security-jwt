package org.tggc.authenticationservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.tggc.authapi.dto.AuthenticationRs;
import org.tggc.authapi.dto.RegisterRq;
import org.tggc.authenticationservice.dto.domain.UserRs;
import org.tggc.authenticationservice.exception.UserAlreadyCreatedException;
import org.tggc.authenticationservice.exception.UserNotFoundException;
import org.tggc.authenticationservice.mapper.UserMapper;
import org.tggc.authenticationservice.model.User;
import org.tggc.authenticationservice.repository.UserRepository;
import org.tggc.authenticationservice.service.AuthenticationService;
import org.tggc.authenticationservice.service.UserService;
import org.tggc.userapi.dto.UserDto;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final AuthenticationService authenticationService;

    @Override
    public AuthenticationRs register(RegisterRq request) {
        userRepository.findByEmail(request.email())
                .ifPresent(u -> {
                    log.error("User with email {} already exists", request.email());
                    throw new UserAlreadyCreatedException(u.getEmail());
                });
        return authenticationService.register(request);
    }

    @Override
    public UserRs getByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(email));
        return userMapper.toDto(user);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public void save(UserRs dto) {
        User user = userMapper.toEntity(dto);
        userRepository.save(user);
    }

    @Override
    public User findById(String id) {
        return userRepository.findById(Long.getLong(id))
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    @Override
    public UserDto getById(long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        return userMapper.toApiDto(user);
    }

    @Override
    public List<UserDto> getByIds(List<Long> ids) {
        return userRepository.findAllById(ids).stream()
                .map(userMapper::toApiDto)
                .toList();
    }

    @Override
    public String getEmailById(Long userId) {
        return userRepository.findById(userId)
                .map(User::getEmail)
                .orElseThrow(() -> new UserNotFoundException(userId));
    }
}
