package ru.tggc.SecurityJWT.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.tggc.SecurityJWT.model.User;

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

        User user = new User(
                "Lexa",
                "Maklov",
                email,
                "123"
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