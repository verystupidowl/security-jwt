package org.tggc.authenticationservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.tggc.notificationapi.api.CodeApi;
import org.tggc.userapi.api.AuthenticationApi;
import reactivefeign.spring.config.EnableReactiveFeignClients;

@SpringBootApplication
@EnableFeignClients(clients = CodeApi.class)
@EnableReactiveFeignClients(clients = AuthenticationApi.class)
public class AuthenticationServiceApplication {

    static void main(String[] args) {
        SpringApplication.run(AuthenticationServiceApplication.class, args);
    }
}
