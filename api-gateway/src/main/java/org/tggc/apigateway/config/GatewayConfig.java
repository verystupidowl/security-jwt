package org.tggc.apigateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("auth-service", r -> r
                        .path("/api/v1/auth/**")
                        .uri("http://localhost:8081/auth"))
                .route("event-service", r -> r
                        .path("/api/v1/events/**")
                        .uri("http://localhost:8082"))
                .route("user-service", r -> r
                        .path("/api/v1/user/**")
                        .uri("http://localhost:8083"))
                .build();
    }
}
