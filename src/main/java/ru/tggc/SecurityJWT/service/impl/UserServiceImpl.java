package ru.tggc.SecurityJWT.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.tggc.SecurityJWT.model.User;
import ru.tggc.SecurityJWT.repository.UserRepository;
import ru.tggc.SecurityJWT.service.UserService;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public List<User> findAll() {
        List<User> all = userRepository.findAll();
        return all;
    }
}
