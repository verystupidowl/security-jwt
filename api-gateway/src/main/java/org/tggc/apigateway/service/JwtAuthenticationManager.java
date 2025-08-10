package org.tggc.apigateway.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@RequiredArgsConstructor
public class JwtAuthenticationManager implements ReactiveAuthenticationManager {
    private final JwtService jwtService;

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        String token = authentication.getCredentials().toString();
        if (jwtService.validateToken(token)) {
            String username = jwtService.getUsername(token);
            List<SimpleGrantedAuthority> authorities = jwtService.getRoles(token)
                    .stream()
                    .map(SimpleGrantedAuthority::new)
                    .toList();
            return Mono.just(new UsernamePasswordAuthenticationToken(username, token, authorities));
        }
        return Mono.empty();
    }
}
