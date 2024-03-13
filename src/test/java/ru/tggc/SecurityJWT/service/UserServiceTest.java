package ru.tggc.SecurityJWT.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.tggc.SecurityJWT.model.Note;
import ru.tggc.SecurityJWT.model.User;
import ru.tggc.SecurityJWT.repository.UserRepository;
import ru.tggc.SecurityJWT.service.impl.UserServiceImpl;
import ru.tggc.SecurityJWT.util.UserMapper;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.when;
import static ru.tggc.SecurityJWT.model.NoteType.LONG;
import static ru.tggc.SecurityJWT.model.NoteType.SHORT;
import static ru.tggc.SecurityJWT.model.Role.USER;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;
    private AutoCloseable autoCloseable;
    private UserService userService;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        userService = new UserServiceImpl(userRepository, userMapper);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void ShouldFindAllUsers() {
        User user1 = new User(
                1L,
                "Lexa",
                "Maklov",
                "123@123.123",
                "123",
                List.of(new Note(1, "123", SHORT, "123", "123", "123", null)),
                USER
        );
        User user2 = new User(
                1L,
                "asd",
                "asd",
                "asd@asd.asd",
                "asd",
                List.of(new Note(1, "123", LONG, "asd", "asd", "asd", null)),
                USER
        );
        List<User> userList = List.of(user1, user2);
        when(userRepository.findAll()).thenReturn(userList);

        List<User> users = userService.findAll();
//        verify(userRepository).findAll();
        assertSame(users, userList);
    }
}