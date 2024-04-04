package ru.tggc.SecurityJWT.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.tggc.SecurityJWT.model.Note;
import ru.tggc.SecurityJWT.model.User;
import ru.tggc.SecurityJWT.service.NoteService;

import java.util.Collections;

import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.tggc.SecurityJWT.model.NoteType.LONG;
import static ru.tggc.SecurityJWT.model.Role.USER;

@AutoConfigureMockMvc
@SpringBootTest
class NoteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private NoteService noteService;

    private User testUser;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        testUser = User.builder()
                .id(1L)
                .email("test@123.123")
                .firstname("Lexa")
                .lastname("123")
                .password("123")
                .role(USER)
                .build();
    }

    @Test
    void testFindAllNotes() throws Exception {
        Note note = new Note();
        when(noteService.findAll()).thenReturn(Collections.singletonList(note));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/note/")
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(note.getId()));
    }

    @Test
    void testFindNotesByUser() throws Exception {
        User user = new User();
        when(noteService.findByOwner(user)).thenReturn(Collections.emptyList());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/note/myNotes")
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    void testFindById() throws Exception {
        Note note = new Note();
        when(noteService.findById(1L)).thenReturn(note);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/note/1")
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(note.getId()));
    }

    @Test
    void testFindByColor() throws Exception {
        User user = new User();
        when(noteService.findByColorAndUser("#FFFFFF", user)).thenReturn(Collections.emptyList());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/note/color/FFFFFF")
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    void testAddNote() throws Exception {
        Note note = new Note(
                1L,
                "123",
                LONG,
                "123",
                "123",
                "123",
                testUser
        );
        when(noteService.save(note, testUser)).thenReturn(note);

        String content = objectMapper.writeValueAsString(note);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/note")
                        .contentType(APPLICATION_JSON)
                        .content(content)
                        .principal(new UsernamePasswordAuthenticationToken(testUser, null))
                )
                .andExpect(status().isCreated());
    }

    @Test
    void testDeleteNote() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/note/1")
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void testEditNote() throws Exception {
        User user = new User();

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/note/edit")
                        .contentType(APPLICATION_JSON)
                        .content("{\"id\":1,\"title\":\"Updated Title\",\"content\":\"Updated Content\",\"color\":\"#FFFFFF\"}")
                        .principal(new UsernamePasswordAuthenticationToken(user, null))
                )
                .andExpect(status().isOk());
    }
}