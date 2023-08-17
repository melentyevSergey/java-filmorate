package ru.yandex.practicum.filmorate.storage.dao;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.MpaStorage;
import ru.yandex.practicum.filmorate.storage.mappers.MpaMapper;
import ru.yandex.practicum.filmorate.utils.NotFoundException;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Primary
@Repository
@AllArgsConstructor
public class MpaDbStorage implements MpaStorage {

    /** Поле с экземпляром сущности для обвязки над JDBC */
    private final JdbcTemplate jdbcTemplate;

    /** Поле с экземпляром сущности маппера строки на класс Mpa */
    private final MpaMapper mpaMapper;

    @Override
    public List<Mpa> getAll() {
        String sqlQuery = "SELECT * FROM mpa";
        List<Mpa> result = new ArrayList<>(jdbcTemplate.query(sqlQuery, mpaMapper));
        log.debug("Текущей размер списка возрастных ограничений: {}", result.size());
        return result;
    }

    @Override
    public Mpa getById(Integer rateId) {
        isMpaPresent(rateId);
        String sqlQuery = "SELECT * FROM mpa WHERE mpa_id = ?";
        log.debug("Запрос возрастного ограничения под номером {}", rateId);
        return jdbcTemplate.queryForObject(sqlQuery, mpaMapper, rateId);
    }

    @Override
    public boolean isMpaPresent(Integer id) {
        String query = "SELECT COUNT (*) FROM MPA WHERE mpa_id = ?";
        if (jdbcTemplate.queryForObject(query, Integer.class, id) == 0) {
            throw new NotFoundException(String.format("Ограничение по возрасту " +
                    "с идентификатором %s не найдено", id));
        }
        return true;
    }
}