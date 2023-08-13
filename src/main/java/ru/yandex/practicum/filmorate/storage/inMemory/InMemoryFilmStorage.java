package ru.yandex.practicum.filmorate.storage.inMemory;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.util.*;

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
    public Film addLike(int id, int userId) {
        Film film = films.get(id);
        film.addLike(userId);

        log.debug("Добавлен лайк пользователя {} фильму {}", userId, id);

        return film;
    }

    @Override
    public Film removeLike(int id, int userId) {
        Film film = films.get(id);
        film.removeLike(userId);

        log.debug("Удален лайк пользователя {} у фильма {}", userId, id);

        return film;
    }

    @Override
    public List<Film> getPopularByCount(int count) {
        int filmsCount = films.values().size();
        int maxCount = Math.min(filmsCount, count);

        List<Film> result = new ArrayList<>(films.values());
        Collections.sort(result, Comparator.comparing(Film::getLikesCount));
        Collections.reverse(result);

        return result.subList(0, maxCount);
    }
}
