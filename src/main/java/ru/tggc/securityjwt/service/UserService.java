package ru.tggc.securityjwt.service;

import org.springframework.stereotype.Service;
import ru.tggc.securityjwt.dto.AuthenticationRs;
import ru.tggc.securityjwt.dto.RegisterRq;
import ru.tggc.securityjwt.dto.UserDto;
import ru.tggc.securityjwt.model.User;

import java.util.List;

@Service
public interface UserService {

    AuthenticationRs register(RegisterRq request);

    List<User> findAll();

    void save(UserDto dto);
}
