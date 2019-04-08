DROP TABLE IF EXISTS `book_genre`;
DROP TABLE IF EXISTS `book_author`;
DROP TABLE IF EXISTS `comments`;
DROP TABLE IF EXISTS `authors`;
DROP TABLE IF EXISTS `genres`;
DROP TABLE IF EXISTS `books`;

CREATE TABLE `authors`
(
    `id`         bigint(20) NOT NULL AUTO_INCREMENT,
    `first_name` varchar(255) DEFAULT NULL,
    `last_name`  varchar(255) DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci;

CREATE TABLE `book_author`
(
    `book_id`   bigint(20) NOT NULL,
    `author_id` bigint(20) NOT NULL,
    PRIMARY KEY (`book_id`, `author_id`),
    KEY `FKro54jqpth9cqm1899dnuu9lqg` (`author_id`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci;

CREATE TABLE `book_genre`
(
    `book_id`  bigint(20) NOT NULL,
    `genre_id` bigint(20) NOT NULL,
    PRIMARY KEY (`book_id`, `genre_id`),
    KEY `FKnkh6m50njl8771p0ll3lylqp2` (`genre_id`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci;

CREATE TABLE `books`
(
    `id`      bigint(20) NOT NULL AUTO_INCREMENT,
    `title`   varchar(255) DEFAULT NULL,
    `written` varchar(255) DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci;

CREATE TABLE `comments`
(
    `id`         bigint(20) NOT NULL AUTO_INCREMENT,
    `commentary` varchar(255) DEFAULT NULL,
    `fk_book_id` bigint(20)   DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY `FKj4uh6ap5rhoiandvo2dte065t` (`fk_book_id`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci;

CREATE TABLE `genres`
(
    `id`    bigint(20) NOT NULL AUTO_INCREMENT,
    `genre` varchar(255) DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `UK_f5jr1xmplevnsodj7nhn12lp5` (`genre`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci;

ALTER TABLE `book_author` ADD CONSTRAINT `FK91ierknt446aaqnjl4uxjyls3` FOREIGN KEY (`book_id`) REFERENCES `books` (`id`) ON DELETE CASCADE;
ALTER TABLE `book_author` ADD CONSTRAINT `FKro54jqpth9cqm1899dnuu9lqg` FOREIGN KEY (`author_id`) REFERENCES `authors` (`id`) ON DELETE CASCADE;
ALTER TABLE `book_genre` ADD CONSTRAINT `FKnkh6m50njl8771p0ll3lylqp2` FOREIGN KEY (`genre_id`) REFERENCES `genres` (`id`) ON DELETE CASCADE;
ALTER TABLE `book_genre` ADD CONSTRAINT `FKq0f88ptislu8lv230mvgonssl` FOREIGN KEY (`book_id`) REFERENCES `books` (`id`) ON DELETE CASCADE;
ALTER TABLE `comments` ADD CONSTRAINT `FKj4uh6ap5rhoiandvo2dte065t` FOREIGN KEY (`fk_book_id`) REFERENCES `books` (`id`) ON DELETE CASCADE;
