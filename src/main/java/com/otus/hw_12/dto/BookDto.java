package com.otus.hw_12.dto;

import com.otus.hw_12.domain.Author;
import com.otus.hw_12.domain.Book;
import com.otus.hw_12.domain.Genre;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
public class BookDto {

    @NotBlank
    private String title;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @NotBlank
    private String genre;

    @NotBlank
    @Pattern(
        regexp = "\\d{4}",
        message = "Invalid year format. Must be 4 digits"
    )
    private String year;

    public Book toEntity(final BookDto bookDto) {
        final Author author = new Author();
        author.setFirstName(bookDto.getFirstName());
        author.setLastName(bookDto.getLastName());

        final Genre genre = new Genre();
        genre.setGenreName(bookDto.getGenre());

        final Book book = new Book();
        book.setTitle(bookDto.getTitle());
        book.setYear(bookDto.getYear());
        book.addAuthor(author);
        book.addGenre(genre);
        return book;
    }

}
