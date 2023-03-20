package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Film;

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
     * Метод для удаления фильма
     * @param id - уникальный идентификатор удаляемого фильма
     */
    void removeFilm(int id);
}
