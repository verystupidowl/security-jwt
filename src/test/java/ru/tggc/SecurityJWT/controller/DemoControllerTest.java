package ru.tggc.SecurityJWT.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.tggc.securityjwt.controller.DemoController;
import ru.tggc.securityjwt.model.Note;
import ru.tggc.securityjwt.model.User;
import ru.tggc.securityjwt.service.UserService;

import java.util.List;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(MockitoExtension.class)
class DemoControllerTest {

    @Mock
    private UserService userService;

    private DemoController demoController;

    private AutoCloseable closeable;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        closeable = openMocks(this);
        demoController = new DemoController(this.userService);
        mockMvc = MockMvcBuilders.standaloneSetup(demoController).build();
        objectMapper = new ObjectMapper();
    }

    @SneakyThrows
    @AfterEach
    void tearDown() {
        closeable.close();
    }

    @Test
    void sayHello() {
    }

    @SneakyThrows
    @Test
    void getAll() {
        Note e1 = new Note(
                1L,
                "asd",
                LONG,
                "asd",
                "asd",
                "asd",
                null
        );
        User user = new User(
                4L,
                "Lexa",
                "Maklov",
                "amaklov",
                "123",
                List.of(e1),
                USER
        );
        List<User> users = List.of(user);
        when(userService.findAll()).thenReturn(users);
        mockMvc.perform(get("/api/v1/demo-controller/get"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(4L));
        verify(userService, times(1)).findAll();
    }

    @SneakyThrows
    @Test
    void saveUser() {
        UserDTO user = new UserDTO(
                "Lexa",
                "Maklov",
                "amaklov2002@gmail.com"
        );
        System.out.println(objectMapper.writeValueAsString(user));
        mockMvc.perform(
                        post("/api/v1/demo-controller/")
                                .contentType(APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(user))
                )
                .andExpect(status().isCreated());
        verify(userService, times(1)).save(user);
    }
}