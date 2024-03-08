package ru.tggc.SecurityJWT.service;

import org.springframework.stereotype.Service;
import ru.tggc.SecurityJWT.dto.UserDTO;
import ru.tggc.SecurityJWT.model.User;

import java.util.List;
import java.util.Optional;

@Service
public interface UserService {

    Optional<User> findByEmail(String email);

    List<User> findAll();

    void save(UserDTO dto);
}
