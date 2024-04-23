package ru.tggc.securityjwt.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.tggc.securityjwt.dto.UserDTO;
import ru.tggc.securityjwt.model.User;
import ru.tggc.securityjwt.repository.UserRepository;
import ru.tggc.securityjwt.service.UserService;
import ru.tggc.securityjwt.util.mapper.UserMapper;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public void save(UserDTO dto) {
        User user = userMapper.toEntity(dto);
        userRepository.save(user);
    }
}
