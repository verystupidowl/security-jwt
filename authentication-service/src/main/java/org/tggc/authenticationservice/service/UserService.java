package org.tggc.authenticationservice.service;

import org.springframework.stereotype.Service;
import org.tggc.authapi.dto.AuthenticationRs;
import org.tggc.authapi.dto.RegisterRq;
import org.tggc.authenticationservice.dto.domain.UserDto;
import org.tggc.authenticationservice.model.User;

import java.util.List;

@Service
public interface UserService {

    AuthenticationRs register(RegisterRq request);

    UserDto getByEmail(String email);

    List<User> findAll();

    void save(UserDto dto);

    User findById(String id);
}
