package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

@Slf4j
@RestController
@Validated
@RequiredArgsConstructor
public class FilmController {

    /** Поле сервисного слоя фильмотеки */
    private final FilmService filmService;

    @GetMapping("/films")
    public List<Film> findAll() {
        log.debug("Получен запрос на получение всех фильмов.");
        return filmService.getFilms();
    }

    @PostMapping(value = "/films")
    public Film addFilm(@Valid @RequestBody Film film) {
        log.debug("Получен запрос на добавление фильма {}.", film.getName());
        return filmService.createNewFilm(film);
    }

    @PutMapping(value = "/films")
    public Film changeFilm(@Valid @RequestBody Film film) {
        log.debug("Получен запрос на обновление фильма {}.", film.getName());
        return filmService.updateFilm(film);
    }

    @GetMapping("/films/{filmId}")
    public Film getFilmById(@PathVariable int filmId) {
        log.debug("Получен запрос на получение фильма по идентификатору {}.", filmId);
        return filmService.getFilmById(filmId);
    }

    @DeleteMapping("/films/{filmId}")
    public void removeFilmById(@PathVariable int filmId) {
        log.debug("Получен запрос на удаление фильма по идентификатору {}.", filmId);
        filmService.removeFilm(filmId);
    }

    @PutMapping("/films/{filmId}/like/{userId}")
    public void addLike(@PathVariable int filmId, @PathVariable int userId) {
        log.debug("Получен запрос на добавления лайка фильма {} пользователем {}.", filmId, userId);
        filmService.addLike(filmId, userId);
    }

    @DeleteMapping("/films/{filmId}/like/{userId}")
    public void removeLike(@PathVariable int filmId, @PathVariable int userId) {
        log.debug("Получен запрос на удаление лайка пользователя {} у фильма {}.", userId, filmId);
        filmService.removeLike(filmId, userId);
    }

    @GetMapping("/films/popular")
    public List<Film> getPopularFilms(
            @RequestParam(defaultValue = "10")
            @Min(value = 1, message = "В запросе задано отрицательное или равное нолю колличество фильмов")
            Integer count) {
        log.debug("Получен запрос на получение топ-{} популярных фильмов.", count);
        return filmService.getPopularByCount(count);
    }
}