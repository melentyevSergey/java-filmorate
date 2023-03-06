package ru.yandex.practicum.filmorate.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.utils.ValidationException;
import ru.yandex.practicum.filmorate.validators.ValidateFilm;

import java.util.*;


@RestController
public class FilmController {

    /** Поле со статическим экземпляром логгера */
    private final static Logger log = LoggerFactory.getLogger(FilmController.class);

    /** Поле с таблицей уникальный идентификатор и фильм */
    private final Map<Integer, Film> films = new HashMap<>();

    /** Поле с последним свободным уникальным идентификатором */
    private int uid = 0;

    @GetMapping("/films")
    public List<Film> getFilms() {
        // получение всех фильмов
        // Обработка GET-запроса по пути "/api/v1/film"

        log.info("Получен запрос GET на получение списка всех фильмов.");
        log.debug("Текущее количество фильмов: {}", films.values().size());
        return new ArrayList<>(films.values());
    }

    @PostMapping(value = "/films")
    public Film create(@Valid @RequestBody Film film) {
        // добавление фильма
        log.info("Получен запрос POST для создания нового фильма.");

        ValidateFilm.validate(film);

        if (films.containsKey(film.getId())) {
            log.debug("Фильм с таким идентификатором уже существует.");
            throw new ValidationException("Фильм с идентификатором " + film.getId() +
                    " уже существует.");
        }

        film.setId(++uid);
        films.put(uid, film);

        log.debug("Создан новый фильм: {}", film);
        return film;
    }

    @PutMapping(value = "/films")
    public Film update(@Valid @RequestBody Film film) {
        // обновление фильма
        log.info("Получен запрос PUT для обновления существующего фильма.");

        int receivedFilmId = film.getId();

        if (films.containsKey(receivedFilmId)) {
            ValidateFilm.validate(film);
            films.put(uid, film);
        } else {
            throw new ValidationException("Нет фильма с таким идентификатором.");
        }

        log.debug("Обновлен фильм: {}", film);
        return film;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ValidationException.class)
    public String validationException(ValidationException exception) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(exception.getMessage());
    }

    public void clearFilms() {
        uid = 0;
        films.clear();
    }
}
