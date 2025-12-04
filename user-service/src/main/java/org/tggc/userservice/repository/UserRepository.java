package org.tggc.userservice.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import org.tggc.userservice.model.User;
import reactor.core.publisher.Mono;

@Repository
public interface UserRepository extends ReactiveCrudRepository<User, Long> {

    Mono<User> findByEmail(String email);
}
