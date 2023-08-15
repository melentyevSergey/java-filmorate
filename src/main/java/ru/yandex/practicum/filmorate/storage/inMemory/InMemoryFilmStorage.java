package ru.yandex.practicum.filmorate.storage.inMemory;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Repository
public class InMemoryFilmStorage implements FilmStorage {

    /** Поле с последним свободным уникальным идентификатором */
    private int uid = 0;

    /** Поле с таблицей уникальный идентификатор и фильм */
    private final Map<Integer, Film> films = new HashMap<>();

    private final Map<Integer, Set<Integer>> likes = new HashMap<>();

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
    public Film getFilmById(int id) {
        log.debug("Запрос фильма с идентификатором {}", id);

        return films.get(id);
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
    public void addLike(int filmId, int userId) {
        log.debug("Пользователь {} поставил лайк фильму {}", userId, filmId);
        likes.get(filmId).add(userId);
    }

    @Override
    public void removeLike(int filmId, int userId) {
        log.debug("Пользователю {} больше не нравится фильм {}", userId, filmId);
        likes.get(filmId).remove(userId);
    }

    @Override
    public List<Film> getPopularByCount(int count) {
        log.debug("Запрос {} самых популярных фильмов", count);
        return likes.entrySet().stream()
                .sorted((o1, o2) -> Integer.compare(o2.getValue().size(), o1.getValue().size()))
                .map(Map.Entry::getKey)
                .limit(Objects.requireNonNullElse(count, 10))
                .collect(Collectors.toList()).stream()
                .mapToInt(number -> number)
                .mapToObj(films::get)
                .collect(Collectors.toList());
    }

    @Override
    public void removeFilmById(Integer id) {

    }

    @Override
    public boolean isFilmPresent(Integer id) {
        return false;
    }
}
