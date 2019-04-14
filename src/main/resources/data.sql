INSERT INTO authors (first_name, last_name) VALUES ('Lewis', 'Carrol');
INSERT INTO authors (first_name, last_name) VALUES ('Charlotte', 'Bronte');
INSERT INTO authors (first_name, last_name) VALUES ('Miguel', 'de Cervantes');
INSERT INTO authors (first_name, last_name) VALUES ('Herbert', 'Wells');
INSERT INTO authors (first_name, last_name) VALUES ('Leo', 'Tolstoy');
INSERT INTO authors (first_name, last_name) VALUES ('Jane', 'Austen');
INSERT INTO authors (first_name, last_name) VALUES ('Gabriel', 'García Márquez');
INSERT INTO authors (first_name, last_name) VALUES ('Leonardo', 'Fibonacci');
INSERT INTO authors (first_name, last_name) VALUES ('Ilya', 'Ilf');
INSERT INTO authors (first_name, last_name) VALUES ('Yevgeni', 'Petrov');

INSERT INTO books (title, written) VALUES ('Alice in Wonderland', '1865');
INSERT INTO books (title, written) VALUES ('Jane Eyre', '1847');
INSERT INTO books (title, written) VALUES ('Don Quixote', '1615');
INSERT INTO books (title, written) VALUES ('The Time Machine', '1895');
INSERT INTO books (title, written) VALUES ('Anna Karenina', '1878');
INSERT INTO books (title, written) VALUES ('Pride and Prejudice', '1813');
INSERT INTO books (title, written) VALUES ('Childhood', '1852');
INSERT INTO books (title, written) VALUES ('Boyhood', '1854');
INSERT INTO books (title, written) VALUES ('Love in the Time of Cholera', '1985');
INSERT INTO books (title, written) VALUES ('The Book of Calculation', '1202');
INSERT INTO books (title, written) VALUES ('The Twelve Chairs', '1928');
INSERT INTO books (title, written) VALUES ('The Little Golden Calf', '1931');

INSERT INTO genres (genre) VALUES ('Literary realism');
INSERT INTO genres (genre) VALUES ('Fantasy');
INSERT INTO genres (genre) VALUES ('Autobiography');
INSERT INTO genres (genre) VALUES ('Novel');
INSERT INTO genres (genre) VALUES ('Science-fiction');
INSERT INTO genres (genre) VALUES ('Romance');
INSERT INTO genres (genre) VALUES ('Mathematics');
INSERT INTO genres (genre) VALUES ('Children''s Literature');
INSERT INTO genres (genre) VALUES ('Satire');

INSERT INTO comments (commentary, fk_book_id) VALUES ('excellent', 1);
INSERT INTO comments (commentary, fk_book_id) VALUES ('nice', 2);
INSERT INTO comments (commentary, fk_book_id) VALUES ('awesome book', 2);
INSERT INTO comments (commentary, fk_book_id) VALUES ('pretty good', 3);
INSERT INTO comments (commentary, fk_book_id) VALUES ('very good', 5);

INSERT INTO book_genre (book_id, genre_id) VALUES (1, 2);
INSERT INTO book_genre (book_id, genre_id) VALUES (1, 8);
INSERT INTO book_genre (book_id, genre_id) VALUES (2, 4);
INSERT INTO book_genre (book_id, genre_id) VALUES (2, 6);
INSERT INTO book_genre (book_id, genre_id) VALUES (3, 4);
INSERT INTO book_genre (book_id, genre_id) VALUES (4, 5);
INSERT INTO book_genre (book_id, genre_id) VALUES (5, 1);
INSERT INTO book_genre (book_id, genre_id) VALUES (5, 4);
INSERT INTO book_genre (book_id, genre_id) VALUES (2, 3);
INSERT INTO book_genre (book_id, genre_id) VALUES (6, 6);
INSERT INTO book_genre (book_id, genre_id) VALUES (7, 3);
INSERT INTO book_genre (book_id, genre_id) VALUES (7, 1);
INSERT INTO book_genre (book_id, genre_id) VALUES (7, 4);
INSERT INTO book_genre (book_id, genre_id) VALUES (8, 3);
INSERT INTO book_genre (book_id, genre_id) VALUES (8, 1);
INSERT INTO book_genre (book_id, genre_id) VALUES (8, 4);
INSERT INTO book_genre (book_id, genre_id) VALUES (9, 4);
INSERT INTO book_genre (book_id, genre_id) VALUES (10, 7);
INSERT INTO book_genre (book_id, genre_id) VALUES (11, 4);
INSERT INTO book_genre (book_id, genre_id) VALUES (11, 9);
INSERT INTO book_genre (book_id, genre_id) VALUES (12, 4);
INSERT INTO book_genre (book_id, genre_id) VALUES (12, 9);

INSERT INTO book_author (book_id, author_id) VALUES (1, 1);
INSERT INTO book_author (book_id, author_id) VALUES (2, 2);
INSERT INTO book_author (book_id, author_id) VALUES (3, 3);
INSERT INTO book_author (book_id, author_id) VALUES (4, 4);
INSERT INTO book_author (book_id, author_id) VALUES (5, 5);
INSERT INTO book_author (book_id, author_id) VALUES (6, 6);
INSERT INTO book_author (book_id, author_id) VALUES (7, 5);
INSERT INTO book_author (book_id, author_id) VALUES (8, 5);
INSERT INTO book_author (book_id, author_id) VALUES (9, 7);
INSERT INTO book_author (book_id, author_id) VALUES (10, 8);
INSERT INTO book_author (book_id, author_id) VALUES (11, 9);
INSERT INTO book_author (book_id, author_id) VALUES (11, 10);
INSERT INTO book_author (book_id, author_id) VALUES (12, 9);
INSERT INTO book_author (book_id, author_id) VALUES (12, 10);

INSERT INTO users (username, password, is_acc_non_exp, is_acc_non_locked, is_cred_non_exp, is_enabled) VALUES ('admin', '$2a$11$dp4wMyuqYE3KSwIyQmWZFeUb7jCsHAdk7ZhFc0qGw6i5J124imQBi', TRUE, TRUE, TRUE, TRUE);
INSERT INTO users (username, password, is_acc_non_exp, is_acc_non_locked, is_cred_non_exp, is_enabled) VALUES ('jdoe', '$2a$11$3NO32OV1TGjap3xMpAEjmuiizitWuaSwUYz42aMtlxRliwJ8zm4Sm', TRUE, TRUE, TRUE, TRUE);

INSERT INTO auth_user_group (username, auth_group) VALUES ('admin', 'USER');
INSERT INTO auth_user_group (username, auth_group) VALUES ('admin', 'ADMIN');
INSERT INTO auth_user_group (username, auth_group) VALUES ('jdoe', 'USER');
