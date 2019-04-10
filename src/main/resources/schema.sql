DROP TABLE IF EXISTS book_genre;
DROP TABLE IF EXISTS book_author;
DROP TABLE IF EXISTS comments;
DROP TABLE IF EXISTS authors;
DROP TABLE IF EXISTS genres;
DROP TABLE IF EXISTS books;
DROP TABLE IF EXISTS auth_user_group;
DROP TABLE IF EXISTS users;

CREATE TABLE users
(
    id                BIGSERIAL PRIMARY KEY,
    username          VARCHAR(128) NOT NULL UNIQUE,
    password          VARCHAR(256) NOT NULL,
    is_acc_non_exp    BOOLEAN,
    is_acc_non_locked BOOLEAN,
    is_cred_non_exp   BOOLEAN,
    is_enabled        BOOLEAN
);

CREATE TABLE auth_user_group
(
    auth_user_group_id BIGSERIAL,
    username           VARCHAR(128) NOT NULL,
    auth_group         VARCHAR(256) NOT NULL,
    PRIMARY KEY (auth_user_group_id)
);

CREATE TABLE authors
(
    id         BIGSERIAL NOT NULL,
    first_name varchar(255) DEFAULT NULL,
    last_name  varchar(255) DEFAULT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE book_author
(
    book_id   bigint NOT NULL,
    author_id bigint NOT NULL,
    PRIMARY KEY (book_id, author_id)
);

CREATE TABLE book_genre
(
    book_id  bigint NOT NULL,
    genre_id bigint NOT NULL,
    PRIMARY KEY (book_id, genre_id)
);

CREATE TABLE books
(
    id      BIGSERIAL NOT NULL,
    title   varchar(255) DEFAULT NULL,
    written varchar(255) DEFAULT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE comments
(
    id         BIGSERIAL NOT NULL,
    commentary varchar(255) DEFAULT NULL,
    fk_book_id bigint       DEFAULT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE genres
(
    id    bigserial NOT NULL,
    genre varchar(255) DEFAULT NULL,
    PRIMARY KEY (id)
);

ALTER TABLE book_author
    ADD CONSTRAINT FK91ierknt446aaqnjl4uxjyls3 FOREIGN KEY (book_id) REFERENCES books (id) ON DELETE CASCADE;
ALTER TABLE book_author
    ADD CONSTRAINT FKro54jqpth9cqm1899dnuu9lqg FOREIGN KEY (author_id) REFERENCES authors (id) ON DELETE CASCADE;
ALTER TABLE book_genre
    ADD CONSTRAINT FKnkh6m50njl8771p0ll3lylqp2 FOREIGN KEY (genre_id) REFERENCES genres (id) ON DELETE CASCADE;
ALTER TABLE book_genre
    ADD CONSTRAINT FKq0f88ptislu8lv230mvgonssl FOREIGN KEY (book_id) REFERENCES books (id) ON DELETE CASCADE;
ALTER TABLE comments
    ADD CONSTRAINT FKj4uh6ap5rhoiandvo2dte065t FOREIGN KEY (fk_book_id) REFERENCES books (id) ON DELETE CASCADE;
ALTER TABLE auth_user_group
    ADD CONSTRAINT FKd146eeb57479db10096cb59e8 FOREIGN KEY (username) REFERENCES users (username) ON DELETE CASCADE;

