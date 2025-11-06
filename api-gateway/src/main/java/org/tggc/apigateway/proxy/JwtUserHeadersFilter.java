package org.tggc.apigateway.proxy;

import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.tggc.apigateway.principal.JwtUserPrincipal;
import reactor.core.publisher.Mono;

import java.util.Collection;
import java.util.stream.Collectors;

import static org.springframework.security.config.annotation.rsocket.PayloadInterceptorOrder.AUTHENTICATION;

@Component
@RequiredArgsConstructor
public class JwtUserHeadersFilter implements GlobalFilter, Ordered {

    @Override
    public int getOrder() {
        return AUTHENTICATION.getOrder() + 1;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        return ReactiveSecurityContextHolder.getContext()
                .map(context -> {
                    Authentication auth = context.getAuthentication();
                    ServerHttpRequest mutatedRequest = exchange.getRequest().mutate()
                            .header("X-User-Name", auth.getName())
                            .header("X-User-Id", getId(auth))
                            .header("X-User-roles", getRoles(auth.getAuthorities()))
                            .build();

                    return exchange.mutate()
                            .request(mutatedRequest)
                            .build();
                })
                .defaultIfEmpty(exchange)
                .flatMap(chain::filter);
    }

    private String getRoles(Collection<? extends GrantedAuthority> authorities) {
        return authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));
    }

    private String getId(Authentication auth) {
        if (auth.getPrincipal() instanceof JwtUserPrincipal principal) {
            return String.valueOf(principal.id());
        }
        return null;
    }
}
