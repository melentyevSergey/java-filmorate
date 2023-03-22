package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

public interface FilmStorage {
    /**
     * Метод для получения всех фильмов
     * @return - список всех фильмов
     */
    List<Film> getFilms();

    /**
     * Метод для создания нового фильма
     * @param film - сущность нового фильма
     * @return сущность нового фильма с уникальным идентификатором
     */
    Film createFilm(Film film);

    /**
     * Метод для обновления фильма
     * @param film - сущность фильма
     * @return сущность измененного фильма
     */
    Film updateFilm(Film film);

    /**
     * Метод для добавления лайка
     * @param id - уникальный идентификатор фильма
     * @param userId - уникальный идентификатор пользователя
     *
     * @return - сущность фильма с обновленным списком лайков
     */
    Film addLike(int id, int userId);

    /**
     * Метод для удаления лайка
     * @param id - уникальный идентификатор фильма
     * @param userId - уникальный идентификатор пользователя
     *
     * @return - сущность фильма с обновленным списком лайков
     */
    Film removeLike(int id, int userId);

    /**
     * Метод для получения топ фильмов по количеству лайков
     * @param count - количество фильмов в топе по лайкам
     *
     * @return - список популярных фильмов
     */
    List<Film> getPopularByCount(int count);

    /**
     * Метод для получения фильма по идентификатору
     * @param id - уникальный идентификатор фильма
     *
     * @return - сущность фильма
     */
    Film getFilmById(int id);
}
