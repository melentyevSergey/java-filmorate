package ru.yandex.practicum.filmorate.service;

import java.util.List;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;
import ru.yandex.practicum.filmorate.utils.FilmNotFoundException;
import ru.yandex.practicum.filmorate.validators.ValidateFilm;

@Slf4j
@Service
@RequiredArgsConstructor
public class FilmService {

    /** Поле слоя хранения фильмотеки */
    private final FilmStorage filmStorage;

    /** Поле слоя хранения пользователей */
    private final UserStorage userStorage;

    /**
     * Метод получения всех фильмов
     * @return возвращает лист всех фильмов
     */
    public List<Film> getFilms() {
        return filmStorage.getFilms();
    }

    /**
     * Метод создания нового фильма
     * @param film - сущность нового фильма
     * @return возвращает вновь созданный фильм
     */
    public Film createNewFilm(Film film) {
        ValidateFilm.validate(film);
        return filmStorage.createFilm(film);
    }

    /**
     * Метод обновления фильма
     * @param film - сущность обновляемого фильма
     * @return возвращает обновленный фильм
     */
    public Film updateFilm(Film film) {
        if (!filmStorage.isFilmPresent(film.getId())) {
            throw new FilmNotFoundException("Нет фильма с таким идентификатором.");
        }

        ValidateFilm.validate(film);
        return filmStorage.updateFilm(film);
    }

    /**
     * Метод добавления лайка фильму
     * @param filmId - идентификатор фильма
     * @param userId - идентификатор пользователя
     */
    public void addLike(int filmId, int userId) {
        if (userStorage.isUserPresent(userId) && filmStorage.isFilmPresent(filmId)) {
            filmStorage.addLike(filmId, userId);
        }
    }

    /**
     * Метод удаления лайка у фильма
     * @param filmId - идентификатор фильма
     * @param userId - идентификатор пользователя
     */
    public void removeLike(int filmId, int userId) {
        if (userStorage.isUserPresent(userId) && filmStorage.isFilmPresent(filmId)) {
            filmStorage.removeLike(filmId, userId);
        }
    }

    /**
     * Метод получения листа популярных фильмов
     * @param count - количество фильмов в листе
     * @return лист фильмов
     */
    public List<Film> getPopularByCount(int count) {
        return filmStorage.getPopularByCount(count);
    }

    /**
     * Метод получения фильма по идентификатору
     * @param id - идентификатор фильма
     * @return сущьность фильма
     */
    public Film getFilmById(int id) {
        if (filmStorage.isFilmPresent(id)) {
            return filmStorage.getFilmById(id);
        } else {
            throw new FilmNotFoundException("Нет фильма с таким идентификатором.");
        }
    }

    /**
     * Метод удаления фильма по идентификатору
     * @param id - идентификатор фильма
     */
    public void removeFilm(Integer id) {
        if (filmStorage.isFilmPresent(id)) {
            filmStorage.removeFilmById(id);
        }
    }
}
