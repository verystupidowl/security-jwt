package org.tggc.authenticationservice.repository;

import lombok.NonNull;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import org.tggc.authenticationservice.model.UserCredentials;
import reactor.core.publisher.Mono;

@Repository
public interface UserRepository extends ReactiveCrudRepository<@NonNull UserCredentials,@NonNull Long> {

    Mono<@NonNull UserCredentials> findByEmail(String email);
}
