package org.tggc.authenticationservice.service;

import org.springframework.stereotype.Service;
import org.tggc.authapi.dto.AuthenticationRs;
import org.tggc.authapi.dto.RegisterRq;
import org.tggc.authenticationservice.dto.domain.UserRs;
import org.tggc.authenticationservice.model.User;
import org.tggc.userapi.dto.UserDto;

import java.util.List;

@Service
public interface UserService {

    AuthenticationRs register(RegisterRq request);

    UserRs getByEmail(String email);

    List<User> findAll();

    void save(UserRs dto);

    User findById(String id);

    UserDto getById(long id);

    List<UserDto> getByIds(List<Long> ids);

}
