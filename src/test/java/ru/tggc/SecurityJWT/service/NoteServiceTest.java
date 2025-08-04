package ru.tggc.SecurityJWT.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.tggc.securityjwt.exception.NoteNotFoundException;
import ru.tggc.securityjwt.model.Note;
import ru.tggc.securityjwt.model.User;
import ru.tggc.securityjwt.repository.NoteRepository;
import ru.tggc.securityjwt.service.NoteService;
import ru.tggc.securityjwt.service.impl.NoteServiceImpl;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

@ExtendWith(MockitoExtension.class)
class NoteServiceTest {

    @Mock
    private NoteRepository noteRepository;

    private NoteService noteService;

    private AutoCloseable closeable;

    @BeforeEach
    void setUp() {
        closeable = openMocks(this);
        this.noteService = new NoteServiceImpl(this.noteRepository);
    }

    @AfterEach
    void tearDown() throws Exception {
        closeable.close();
    }

    @Test
    void findAll() {
        Note note1 = new Note(1,
                "123",
                SHORT,
                "123",
                "123",
                "123",
                null
        );
        Note note2 = new Note(2,
                "qwe",
                LONG,
                "qwe",
                "qwe",
                "qwe",
                null
        );

        List<Note> expectedList = List.of(note1, note2);

        when(noteRepository.findAll()).thenReturn(expectedList);

        List<Note> actualList = noteService.findAll();

        assertSame(expectedList, actualList);
    }

    @Test
    void save() {
        Note note = createNote();
        User user = createUser();

        noteService.save(note, user);

        verify(noteRepository, times(1)).save(note);
    }

    @Test
    void findByOwner() {
        User user = createUser();
        Note note = createNote();
        List<Note> expectedNote = List.of(note);

        when(noteRepository.findByOwner(user)).thenReturn(expectedNote);

        List<Note> actualNote = noteService.findByOwner(user);

        assertSame(expectedNote, actualNote);
    }

    @Test
    void deleteById() {
    }

    @Test
    void editNote() {
    }

    @Test
    void findByIdValid() {
        Optional<Note> expectedNote = Optional.of(createNote());

        when(noteRepository.findById(1L)).thenReturn(expectedNote);

        Note actualNote = noteService.findById(1L);

        assertSame(expectedNote.get(), actualNote);
    }


    @Test
    void findByIdException() {
        when(noteRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NoteNotFoundException.class, () -> noteService.findById(1L));
    }

    @Test
    void findByColorAndUser() {
    }

    private Note createNote() {
        return new Note(1,
                "123",
                SHORT,
                "123",
                "123",
                "123",
                null
        );
    }

    private User createUser() {
        return new User(
                1L,
                "Lexa",
                "Maklov",
                "123@123.123",
                "123",
                List.of(new Note(1, "123", SHORT, "123", "123", "123", null)),
                USER
        );
    }
}