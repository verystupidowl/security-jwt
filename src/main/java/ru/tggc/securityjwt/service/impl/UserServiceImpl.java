package ru.tggc.securityjwt.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.tggc.securityjwt.dto.response.AuthenticationRs;
import ru.tggc.securityjwt.dto.request.RegisterRq;
import ru.tggc.securityjwt.dto.domain.UserDto;
import ru.tggc.securityjwt.exception.UserAlreadyCreatedException;
import ru.tggc.securityjwt.mapper.UserMapper;
import ru.tggc.securityjwt.model.User;
import ru.tggc.securityjwt.repository.UserRepository;
import ru.tggc.securityjwt.service.AuthenticationService;
import ru.tggc.securityjwt.service.UserService;

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
