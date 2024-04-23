package ru.tggc.securityjwt.service;

import org.springframework.stereotype.Service;
import ru.tggc.securityjwt.dto.UserDTO;
import ru.tggc.securityjwt.model.User;

import java.util.List;
import java.util.Optional;

@Service
public interface UserService {

    Optional<User> findByEmail(String email);

    List<User> findAll();

    void save(UserDTO dto);
}
