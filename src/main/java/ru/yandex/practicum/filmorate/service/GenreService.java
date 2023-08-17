package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.GenreStorage;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class GenreService {

    /** Поле слоя хранения жанров */
    private final GenreStorage genreStorage;

    /**
     * Метод получения всех жанров
     * @return лист жанров
     */
    public List<Genre> getAll() {
        return genreStorage.getAll();
    }

    /**
     * Метод получения жанра по уникальному идентификатору
     * @param id - уникальный идентификатор жанра
     * @return сущность жанра
     */
    public Genre getById(Integer id) {
        return genreStorage.getById(id);
    }
}