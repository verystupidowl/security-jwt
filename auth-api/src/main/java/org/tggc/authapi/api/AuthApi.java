package org.tggc.authapi.api;

import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.tggc.authapi.config.FeignConfig;
import org.tggc.authapi.dto.AuthenticationRq;
import org.tggc.authapi.dto.AuthenticationRs;
import org.tggc.authapi.dto.RegisterRq;

import static org.springframework.http.HttpStatus.CREATED;

@FeignClient(name = "auth-service", path = "/auth", configuration = FeignConfig.class)
public interface AuthApi {

    @PostMapping("/authenticate")
    AuthenticationRs authenticate(@Valid @RequestBody AuthenticationRq request);

    @PostMapping("/register")
    @ResponseStatus(CREATED)
    AuthenticationRs register(@Valid @RequestBody RegisterRq request);
}
