package ru.yandex.practicum.filmorate.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.InMemoryFilmStorage;

import java.time.LocalDate;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class FilmControllerTest {
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private InMemoryFilmStorage filmStorage;
    @Autowired
    private MockMvc mockMvc;
    private Film validFilm;
    private Film invalidFilm;

    @BeforeEach
    void beforeEach() {
        validFilm = Film.builder()
                .name("FirstFilmName")
                .description("Description")
                .releaseDate(LocalDate.of(2021, 4, 4))
                .duration(120)
                .build();
    }

    @AfterEach
    void clearRepository() {
        filmStorage.resetFilmStorage();
    }

    @Test
    void shouldBeCreated() throws Exception {
        mockMvc.perform(post("/films")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validFilm)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.name").value("FirstFilmName"))
                .andExpect(jsonPath("$.description").value("Description"))
                .andExpect(jsonPath("$.releaseDate").value("2021-04-04"))
                .andExpect(jsonPath("$.duration").value("120"));
    }

    @Test
    void shouldBeCreateExceptionWithEmptyRequest() throws Exception {
        mockMvc.perform(post("/films")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(null)))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void shouldBeNameException() throws Exception {
        invalidFilm = validFilm.toBuilder()
                .name("")
                .build();
        mockMvc.perform(post("/films")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidFilm)))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void shouldBeDescriptionException() throws Exception {
        invalidFilm = validFilm.toBuilder()
                .description("Nam quis nulla. Integer malesuada. In in enim a" +
                        " arcu imperdiet malesuada. Sed vel lectus. Donec odio urna, " +
                        "tempus molestie, porttitor ut, iaculis quis, sem. Phasellus rhoncus. " +
                        "Aenean id metus id velit ullamcorper pulvina")
                .build();
        mockMvc.perform(post("/films")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidFilm)))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void shouldBeReleaseDateExceptionAfter1895_12_28() throws Exception {
        invalidFilm = validFilm.toBuilder()
                .releaseDate(LocalDate.of(1888, 12, 28))
                .build();
        mockMvc.perform(post("/films")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidFilm)))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void shouldBeReleaseDateExceptionBeforeNow() throws Exception {
        invalidFilm = validFilm.toBuilder()
                .releaseDate(LocalDate.now().plusDays(1))
                .build();
        mockMvc.perform(post("/films")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidFilm)))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void shouldBeCreatedWithReleaseDate1895_12_28() throws Exception {
        invalidFilm = validFilm.toBuilder()
                .releaseDate(LocalDate.of(1895, 12, 28))
                .build();
        mockMvc.perform(post("/films")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidFilm)))
                .andExpect(status().isOk());
    }

    @Test
    void shouldBeCreatedWithReleaseDate1998_09_01() throws Exception {
        invalidFilm = validFilm.toBuilder()
                .releaseDate(LocalDate.of(1998, 9, 1))
                .build();
        mockMvc.perform(post("/films")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidFilm)))
                .andExpect(status().isOk());
    }

    @Test
    void shouldBeDurationExceptionWith0() throws Exception {
        invalidFilm = validFilm.toBuilder()
                .duration(0)
                .build();
        mockMvc.perform(post("/films")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidFilm)))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void shouldBeCreatedWithDuration1() throws Exception {
        invalidFilm = validFilm.toBuilder()
                .duration(1)
                .build();
        mockMvc.perform(post("/films")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidFilm)))
                .andExpect(status().isOk());
    }

    @Test
    void shouldBeCreatedWithDuration300() throws Exception {
        invalidFilm = validFilm.toBuilder()
                .duration(300)
                .build();
        mockMvc.perform(post("/films")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidFilm)))
                .andExpect(status().isOk());
    }

    @Test
    void shouldBeUpdated() throws Exception {
        mockMvc.perform(post("/films")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validFilm)));
        String updName = "NewName";
        String updDescription = "NewDescription";
        LocalDate updReleaseDate = LocalDate.of(1999, 4, 4);
        final int updDuration = 180;
        validFilm = validFilm.toBuilder()
                .id(1)
                .name(updName)
                .description(updDescription)
                .releaseDate(updReleaseDate)
                .duration(updDuration)
                .build();
        mockMvc.perform(put("/films")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validFilm)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.name").value(updName))
                .andExpect(jsonPath("$.description").value(updDescription))
                .andExpect(jsonPath("$.releaseDate").value(updReleaseDate.toString()))
                .andExpect(jsonPath("$.duration").value(updDuration));
    }

    @Test
    void shouldBeUpdateExceptionWithNonexistentId() throws Exception {
        validFilm.setId(999);
        mockMvc.perform(put("/films")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validFilm)))
                .andExpect(status().is5xxServerError());
    }

    @Test
    void shouldBeReturnedList() throws Exception {
        mockMvc.perform(post("/films")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validFilm)))
                .andExpect(status().isOk());
        validFilm.setId(1);

        mockMvc.perform(get("/films")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validFilm)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(1)));
    }
}
