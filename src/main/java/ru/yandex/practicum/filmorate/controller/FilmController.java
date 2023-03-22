package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import java.util.*;

@Slf4j
@RestController
public class FilmController {

    private final FilmService service;

    @Autowired
    public FilmController(FilmService service) {
        this.service = service;
    }

    @ResponseBody
    @GetMapping("/films")
    public List<Film> getFilms() {
        log.info("Получен запрос GET на получение списка всех фильмов.");

        return service.getFilms();
    }

    @ResponseBody
    @GetMapping("/films/{id}")
    public Film getFilmById(@PathVariable int id) {
        log.info("Получен запрос GET для получения фильма по идентификатору {}", id);

        return service.getFilmById(id);
    }

    @ResponseBody
    @PostMapping("/films")
    public Film create(@RequestBody Film film) {
        log.info("Получен запрос POST для создания нового фильма.");

        return service.createNewFilm(film);
    }

    @ResponseBody
    @PutMapping("/films")
    public Film update(@RequestBody Film film) {
        log.info("Получен запрос PUT для обновления существующего фильма.");

        return service.updateFilm(film);
    }

    @ResponseBody
    @PutMapping("/films/{id}/like/{userId}")
    public Film addUserLike(@PathVariable int id, @PathVariable int userId) {
        log.info("Получен запрос PUT для добавления лайка фильму {} пользователем {}", id, userId);

        return service.addLike(id, userId);
    }

    @ResponseBody
    @DeleteMapping("/films/{id}/like/{userId}")
    public Film deleteUserLike(@PathVariable int id, @PathVariable int userId) {
        log.info("Получен запрос DELETE для удаления лайка " +
                "пользователя {} под фильмом {}", userId, id);

        return service.removeLike(id, userId);
    }

    @ResponseBody
    @GetMapping("/films/popular")
    public List<Film> getPopularByCount(@RequestParam(defaultValue = "10") int count) {
        log.info("Получен запрос GET для возврата списка из первых {} фильмов " +
                "по количеству лайков", count);

        return service.getPopularByCount(count);
    }
}
