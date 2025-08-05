package ru.tggc.securityjwt.service;

import org.springframework.stereotype.Service;
import ru.tggc.securityjwt.dto.response.AuthenticationRs;
import ru.tggc.securityjwt.dto.request.RegisterRq;
import ru.tggc.securityjwt.dto.domain.UserDto;
import ru.tggc.securityjwt.model.User;

import java.util.List;

@Service
public interface UserService {

    AuthenticationRs register(RegisterRq request);

    List<User> findAll();

    void save(UserDto dto);
}
