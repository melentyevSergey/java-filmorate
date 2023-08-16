package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Genre;

import java.util.List;

public interface GenreStorage {

    /**
     * Метод для получения всех жанров
     * @return - список всех жанров
     */
    List<Genre> getAll();

    /**
     * Метод получения жанра по идентификатору
     * @param genreId - уникальный идентификатор жанра
     * @return - сущность жанра
     */
    Genre getById(Integer genreId);

    /**
     * Метод для проверки существования жанра по идентификатору
     * @param id - уникальный идентификатор жанра
     * @return - булиевое значение наличия жанра
     */
    boolean isGenrePresent(Integer id);

}