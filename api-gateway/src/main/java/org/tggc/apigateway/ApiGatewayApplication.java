package org.tggc.apigateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.tggc.authapi.api.AuthApi;
import reactivefeign.spring.config.EnableReactiveFeignClients;

@EnableReactiveFeignClients(clients = AuthApi.class)
@SpringBootApplication
public class ApiGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiGatewayApplication.class, args);
    }
}
