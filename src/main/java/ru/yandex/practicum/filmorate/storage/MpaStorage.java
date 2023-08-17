package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Mpa;

import java.util.List;

public interface MpaStorage {

    /**
     * Метод для получения всех MPA
     * @return - список всех MPA
     */
    List<Mpa> getAll();

    /**
     * Метод для получения MPA по идентификатору
     * @param rateId - уникальный идентификатор MPA
     * @return - сущность MPA
     */
    Mpa getById(Integer rateId);

    /**
     * Метод для проверки существования MPA по идентификатору
     * @param id - уникальный идентификатор MPA
     * @return - булиевое значение наличия MPA
     */
    boolean isMpaPresent(Integer id);
}