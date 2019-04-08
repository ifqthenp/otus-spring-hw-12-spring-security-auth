package com.otus.hw_12.repository;

import com.otus.hw_12.domain.Book;
import com.otus.hw_12.dto.BookProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    @Query("SELECT b FROM Book b JOIN b.authors a WHERE " +
        "LOWER(CONCAT(TRIM(a.firstName), ' ' , TRIM(a.lastName))) LIKE " +
        "LOWER(CONCAT('%', TRIM(:name), '%'))")
    Iterable<Book> findBookByAuthorName(@Param("name") String name);

    @Query("SELECT b FROM Book b JOIN b.genres g WHERE " +
        "LOWER(g.genreName) LIKE LOWER(CONCAT('%', TRIM(:gName), '%'))")
    Iterable<Book> findBookByGenreName(@Param("gName") String genreName);

    Iterable<Book> findByTitleContainingIgnoreCase(String title);

    @Query(value = "SELECT" +
        "  books.title                                                                              AS title,\n" +
        "  GROUP_CONCAT(DISTINCT CONCAT(authors.first_name, ' ', authors.last_name) SEPARATOR ', ') AS authors,\n" +
        "  GROUP_CONCAT(DISTINCT genres.genre SEPARATOR ', ')                                       AS genres\n" +
        "FROM books,\n" +
        "     authors,\n" +
        "     genres,\n" +
        "     book_author,\n" +
        "     book_genre\n" +
        "WHERE books.id = book_author.book_id\n" +
        "  AND authors.id = book_author.author_id\n" +
        "  AND books.id = book_genre.book_id\n" +
        "  AND genres.id = book_genre.genre_id\n" +
        "  AND LOWER(books.title) LIKE LOWER(CONCAT('%', TRIM(:title), '%'))\n" +
        "GROUP BY Title", nativeQuery = true)
    List<BookProjection> findBooksByTitle(@Param("title") String title);

    @Query(value = "SELECT" +
        "  books.title                                                                              AS title,\n" +
        "  GROUP_CONCAT(DISTINCT CONCAT(authors.first_name, ' ', authors.last_name) SEPARATOR ', ') AS authors,\n" +
        "  GROUP_CONCAT(DISTINCT genres.genre SEPARATOR ', ')                                       AS genres\n" +
        "FROM books,\n" +
        "     authors,\n" +
        "     genres,\n" +
        "     book_author,\n" +
        "     book_genre\n" +
        "WHERE books.id = book_author.book_id\n" +
        "  AND authors.id = book_author.author_id\n" +
        "  AND books.id = book_genre.book_id\n" +
        "  AND genres.id = book_genre.genre_id\n" +
        "  AND LOWER(books.title) LIKE LOWER(CONCAT('%', TRIM(:title), '%'))\n" +
        "  AND LOWER(CONCAT(TRIM(authors.first_name), ' ', TRIM(authors.last_name))) LIKE LOWER(CONCAT('%', TRIM(:author), '%'))\n" +
        "  AND LOWER(genres.genre) LIKE LOWER(CONCAT('%', TRIM(:genre), '%'))\n" +
        "GROUP BY Title", nativeQuery = true)
    List<BookProjection> findBooksByRequestParameters(@Param("title") String title,
                                                      @Param("author") String author,
                                                      @Param("genre") String genre);

}
