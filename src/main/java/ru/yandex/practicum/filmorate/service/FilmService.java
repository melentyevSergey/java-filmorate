package ru.yandex.practicum.filmorate.service;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.InMemoryFilmStorage;
import ru.yandex.practicum.filmorate.utils.IdValidationException;
import ru.yandex.practicum.filmorate.validators.ValidateFilm;

@Slf4j
@Service
public class FilmService {

    private final InMemoryFilmStorage filmStorage;

    @Autowired
    public FilmService(InMemoryFilmStorage filmStorage) {
        this.filmStorage = filmStorage;
    }

    public List<Film> getFilms() {
        return filmStorage.getFilms();
    }

    public Film createNewFilm(Film film) {
        ValidateFilm.validate(film);

        log.debug("Валидация фильма при создании завершена успешно.");

        return filmStorage.createFilm(film);
    }

    public Film updateFilm(Film film) {
        final int receivedFilmId = film.getId();

        if (filmStorage.isFilmPresent(receivedFilmId)) {
            ValidateFilm.validate(film);

            log.debug("Валидация фильма при обновлении завершена успешно.");

            return filmStorage.updateFilm(film);
        } else {
            throw new IdValidationException("Нет фильма с таким идентификатором.");
        }
    }
}
