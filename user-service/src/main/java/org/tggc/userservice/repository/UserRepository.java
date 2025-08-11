package org.tggc.userservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.tggc.userservice.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
