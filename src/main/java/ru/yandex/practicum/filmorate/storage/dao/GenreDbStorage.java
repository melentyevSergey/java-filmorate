package ru.yandex.practicum.filmorate.storage.dao;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.GenreStorage;
import ru.yandex.practicum.filmorate.storage.mappers.GenreMapper;
import ru.yandex.practicum.filmorate.utils.GenreNotFoundException;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Primary
@Repository
@AllArgsConstructor
public class GenreDbStorage implements GenreStorage {

    /** Поле с экземпляром сущности для обвязки над JDBC */
    private final JdbcTemplate jdbcTemplate;

    /** Поле с экземпляром сущности маппера строки на класс Genre */
    private final GenreMapper genreMapper;

    @Override
    public List<Genre> getAll() {
        String sqlQuery = "SELECT * FROM genre";
        List<Genre> result = new ArrayList<>(jdbcTemplate.query(sqlQuery, genreMapper));
        log.debug("Предоставляется список жанров: {} наименований", result.size());
        return result;
    }

    @Override
    public Genre getById(Integer genreId) {
        isGenrePresent(genreId);
        String sqlQuery = "SELECT * FROM genre WHERE genre_id = ?";
        log.debug("Запрос жанра под номером {}", genreId);
        return jdbcTemplate.queryForObject(sqlQuery, genreMapper, genreId);
    }

    @Override
    public boolean isGenrePresent(Integer id) {
        String query = "SELECT COUNT (*) FROM genre WHERE genre_id = ?";
        if (jdbcTemplate.queryForObject(query, Integer.class, id) == 0) {
            log.debug(String.format("Жанр фильма под номером %s не найден", id));
            throw new GenreNotFoundException(String.format("Жанр фильма под номером %s не найден", id));
        }
        return true;
    }
}