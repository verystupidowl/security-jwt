package org.tggc.authenticationservice.service;

import org.springframework.stereotype.Service;
import org.tggc.authenticationservice.dto.response.AuthenticationRs;
import org.tggc.authenticationservice.dto.request.RegisterRq;
import org.tggc.authenticationservice.dto.domain.UserDto;
import org.tggc.authenticationservice.model.User;

import java.util.List;

@Service
public interface UserService {

    AuthenticationRs register(RegisterRq request);

    List<User> findAll();

    void save(UserDto dto);
}
