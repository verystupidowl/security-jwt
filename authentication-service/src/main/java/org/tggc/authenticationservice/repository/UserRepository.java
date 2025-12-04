package org.tggc.authenticationservice.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import org.tggc.authenticationservice.model.UserCredentials;
import reactor.core.publisher.Mono;

@Repository
public interface UserRepository extends ReactiveCrudRepository<UserCredentials, Long> {

    Mono<UserCredentials> findByEmail(String email);
}
