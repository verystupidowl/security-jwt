package org.tggc.authenticationservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.tggc.authenticationservice.dto.response.AuthenticationRs;
import org.tggc.authenticationservice.dto.request.RegisterRq;
import org.tggc.authenticationservice.dto.domain.UserDto;
import org.tggc.authenticationservice.exception.UserAlreadyCreatedException;
import org.tggc.authenticationservice.mapper.UserMapper;
import org.tggc.authenticationservice.model.User;
import org.tggc.authenticationservice.repository.UserRepository;
import org.tggc.authenticationservice.service.AuthenticationService;
import org.tggc.authenticationservice.service.UserService;

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
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public void save(UserDto dto) {
        User user = userMapper.toEntity(dto);
        userRepository.save(user);
    }
}
