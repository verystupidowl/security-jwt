package org.tggc.userapi.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.tggc.authapi.dto.AuthenticationRs;
import org.tggc.authapi.dto.RegisterRq;
import org.tggc.userapi.dto.UserDto;
import reactivefeign.spring.config.ReactiveFeignClient;
import reactor.core.publisher.Mono;

@ReactiveFeignClient(name = "authenticationApi", path = "api/v1/authenticate")
public interface AuthenticationApi {

    @GetMapping("/get-user")
    Mono<UserDto> getUserByEmail(@RequestBody String email);

    @PostMapping("save-user")
    Mono<AuthenticationRs> saveUser(@RequestBody RegisterRq registerRq);
}
