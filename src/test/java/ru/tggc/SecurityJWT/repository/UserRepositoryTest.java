package ru.tggc.SecurityJWT.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.tggc.securityjwt.model.Note;
import ru.tggc.securityjwt.model.Role;
import ru.tggc.securityjwt.model.User;
import ru.tggc.securityjwt.repository.UserRepository;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(MockitoExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }

    @Test
    void itShouldFindUser() {
        String email = "amaklov2002@gmail.com";
        List<Note> notes = Collections.emptyList();
        Role role = Role.USER;
        User user = new User(
                1L,
                "Lexa",
                "Maklov",
                email,
                "123",
                notes,
                role
        );

        userRepository.save(user);

        boolean exists = userRepository.findByEmail(email).isPresent();

        assertThat(exists).isTrue();
    }

    @Test
    void itShouldNotFindUser() {
        String email = "amaklov2002@gmail.com";

        boolean exists = userRepository.findByEmail(email).isPresent();

        assertThat(exists).isFalse();
    }
}