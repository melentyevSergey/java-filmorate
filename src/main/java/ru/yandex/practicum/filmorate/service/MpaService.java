package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.MpaStorage;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class MpaService {

    /** Поле слоя хранения MPA */
    private final MpaStorage mpaStorage;

    /**
     * Метод получения всех MPA
     * @return лист MPA
     */
    public List<Mpa> getAll() {
        return mpaStorage.getAll();
    }

    /**
     * Метод получения MPA по уникальному идентификатору
     * @param id - уникальный идентификатор жанра
     * @return сущность MPA
     */
    public Mpa getById(Integer id) {
        return mpaStorage.getById(id);
    }
}