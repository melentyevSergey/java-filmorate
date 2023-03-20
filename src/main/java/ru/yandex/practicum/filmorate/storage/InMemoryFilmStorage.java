package ru.yandex.practicum.filmorate.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Repository
public class InMemoryFilmStorage implements FilmStorage {

    /** Поле с последним свободным уникальным идентификатором */
    private int uid = 0;

    /** Поле с таблицей уникальный идентификатор и фильм */
    private final Map<Integer, Film> films = new HashMap<>();

    public boolean isFilmPresent(int id) {
        return films.containsKey(id);
    }

    public void resetFilmStorage() {
        uid = 0;
        films.clear();
    }

    @Override
    public List<Film> getFilms() {
        log.debug("Текущее количество фильмов: {}", films.values().size());

        return new ArrayList<>(films.values());
    }

    @Override
    public Film createFilm(Film film) {
        film.setId(++uid);
        films.put(uid, film);

        log.debug("Создан новый фильм с идентификатором: {}", film.getId());

        return film;
    }

    @Override
    public Film updateFilm(Film film) {
        films.put(film.getId(), film);

        log.debug("Обновлен фильм с идентификатором: {}", film.getId());

        return film;
    }

    @Override
    public void removeFilm(int id) {
        // TODO removeFilm(int id)
    }
}
