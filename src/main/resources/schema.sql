CREATE TABLE "genre"
(
    "genre_id" INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    "name" varchar
);

CREATE TABLE "users"
(
    user_id INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    email varchar,
    login varchar NOT NULL,
    name varchar,
    birthday timestamp,
    friend_user_id integer,
    friend_user_status integer
);

create unique index user_uid_index
    on "users" (user_id);

CREATE TABLE "films"
(
    film_id INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    name varchar NOT NULL,
    description text,
    releaseDate timestamp,
    duration integer,
    liked_user_id integer,
    genre integer,
    rating varchar,
    constraint GENRE_ID_FK
        foreign key (genre) references "genre",
    constraint LIKED_USER_ID_FK
        foreign key (liked_user_id) references "users"
);

create unique index film_uid_index
    on "films" (film_id);