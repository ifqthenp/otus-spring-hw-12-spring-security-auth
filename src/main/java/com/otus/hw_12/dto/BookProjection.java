package com.otus.hw_12.dto;

import com.otus.hw_12.controllers.BookSearchController;
import org.springframework.ui.Model;

/**
 * This projection of book is to be used in search result provided by
 * {@code BookService} and initiated by user in book search form.
 *
 * @see BookSearchController#fullSearch(Model, String, String, String)
 */
public interface BookProjection {

    String getTitle();

    String getAuthors();

    String getGenres();

}
