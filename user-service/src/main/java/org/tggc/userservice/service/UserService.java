package org.tggc.userservice.service;

import org.tggc.userapi.dto.UserDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface UserService {

    Mono<UserDto> getUserById(long id);

    Flux<UserDto> getUsersByIds(List<Long> ids);

    Mono<UserDto> getUserByEmail(String email);
}
