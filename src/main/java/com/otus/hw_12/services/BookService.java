package com.otus.hw_12.services;

import com.otus.hw_12.domain.Book;
import com.otus.hw_12.dto.BookProjection;
import com.otus.hw_12.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

    public long getCount() {
        return bookRepository.count();
    }

    public List<BookProjection> findBooksByRequestParameters(final String title,
                                                             final String author,
                                                             final String genre) {
        return bookRepository.findBooksByRequestParameters(title, author, genre);
    }

    public List<BookProjection> findBooksByTitleRequestParam(final String title) {
        return bookRepository.findBooksByTitle(title);
    }

    public Book saveBook(final Book book) {
        return bookRepository.save(book);
    }

}
