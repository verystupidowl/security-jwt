package org.tggc.userservice.service;

import lombok.NonNull;
import org.tggc.authapi.dto.AuthenticationRs;
import org.tggc.authapi.dto.RegisterRq;
import org.tggc.userapi.dto.UserDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface UserService {

    Mono<@NonNull UserDto> getUserById(long id);

    Flux<@NonNull UserDto> getUsersByIds(List<Long> ids);

    Mono<@NonNull UserDto> getUserByEmail(String email);

    Mono<@NonNull AuthenticationRs> saveUser(RegisterRq registerRq);
}
