package ru.tggc.SecurityJWT.service;

import org.springframework.stereotype.Service;
import ru.tggc.SecurityJWT.model.User;

import java.util.Optional;

@Service
public interface UserService {

    Optional<User> findByEmail(String email);
}
