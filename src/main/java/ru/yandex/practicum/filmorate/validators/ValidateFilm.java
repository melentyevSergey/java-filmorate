package ru.yandex.practicum.filmorate.validators;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.yandex.practicum.filmorate.controller.UserController;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.utils.ValidationException;

import java.time.LocalDate;

import static java.time.Month.DECEMBER;

public class ValidateFilm {

    /** Поле с константой максимальной длины описания */
    private final static long MAX_DESCRIPTION_LENGTH = 200;

    /** Поле с константой наиболее ранней доступной датой релиза */
    private final static LocalDate MOST_EARLY_DATE = LocalDate
            .of(1895, DECEMBER, 28);

    /** Поле со статическим экземпляром логгера */
    private final static Logger log = LoggerFactory.getLogger(UserController.class);

    private ValidateFilm() {}

    public static void validate(Film film) {
        log.info("Запущен процесс валидации полученного фильма.");

        String name = film.getName();
        String description = film.getDescription();
        LocalDate releaseDate = film.getReleaseDate();
        long duration = film.getDuration();

        if (name.isEmpty()) {
            log.debug("Название фильма пустое.");
            throw new ValidationException("Название не может быть пустым.");
        }
        if (description.length() > MAX_DESCRIPTION_LENGTH) {
            log.debug("Размер поля с описанием фильма превышает максимально" +
                    "допустимое и составляет {}", description);
            throw new ValidationException("Максимальная длина описания — 200 символов.");
        }
        if (duration <= 0) {
            log.debug("Размер поля с продолжительностью фильма меньше" +
                    "или равно нулю и составляет {}", duration);
            throw new ValidationException("Продолжительность фильма должна быть положительной.");
        }
        LocalDate now = LocalDate.now();
        if (releaseDate.isAfter(now)) {
            log.debug("Дата релиза фильма не может быть в будущем, передана {} ", releaseDate);
            throw new ValidationException("Дата релиза будущим числом.");
        }
        if (releaseDate.isBefore(MOST_EARLY_DATE)) {
            log.debug("Поле с релизом фильма старше допустимой " +
                    "и составляет {}", releaseDate);
            throw new ValidationException("Дата релиза раньше 28 декабря 1895 года.");
        }
        log.info("Валидация полученного фильма успешно завершена.");
    }
}
