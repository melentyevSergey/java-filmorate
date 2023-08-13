MERGE INTO GENRE (genre_id, genre_name)
    VALUES (1, 'Боевик'),
           (2, 'Документальный'),
           (3, 'Триллер');

MERGE INTO MPA (mpa_id, mpa_name)
    VALUES (1, 'GG'),
           (2, 'PPG'),
           (3, 'PPG-13');

MERGE INTO USERS (user_id, name, email, login, birthday)
    VALUES (1, 'Name_1', 'mail_1@mail.ru', 'Login_1', '2023-08-23'),
           (2, 'Name_2', 'mail_2@mail.ru', 'Login_2', '2023-08-24');

INSERT INTO FRIENDS (friend_one_id, friend_two_id, status)
VALUES (1, 2, TRUE);