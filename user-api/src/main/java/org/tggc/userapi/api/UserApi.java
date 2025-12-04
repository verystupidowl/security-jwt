package org.tggc.userapi.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.tggc.userapi.dto.UserDto;
import reactivefeign.spring.config.ReactiveFeignClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@ReactiveFeignClient(name = "userApi", url = "/api/v1/user")
public interface UserApi {

    @GetMapping("/{id}")
    Mono<UserDto> getUserById(@PathVariable("id") long id);

    @PostMapping("/getByIds")
    Flux<UserDto> getUsers(@RequestBody List<Long> ids);
}
