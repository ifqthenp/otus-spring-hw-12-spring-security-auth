package com.otus.hw_12.controllers;

import com.otus.hw_12.dto.BookDto;
import com.otus.hw_12.domain.Book;
import com.otus.hw_12.dto.BookProjection;
import com.otus.hw_12.services.BookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class LibraryController {

    private final BookService bookService;

    @GetMapping("/library/books/add")
    public String bookAdd(final Model model) {
        return "book_add_new";
    }

    @PostMapping("/library/books/add")
    public String bookAdd(@Valid @ModelAttribute("bookDto") final BookDto bookDto,
                          final BindingResult result) {
        if (result.hasErrors()) {
            log.debug("{}", result);
            return "book_add_new";
        }
        final Book book = bookDto.toEntity(bookDto);
        bookService.saveBook(book);
        return "redirect:/home";
    }

    @GetMapping(value = "/library/books/search")
    public String bookSearchForm() {
        return "book_search_form";
    }

    @GetMapping(value = "/library/books/search/full")
    public String fullSearch(final Model model,
                             @RequestParam(required = false) final String title,
                             @RequestParam(required = false) final String author,
                             @RequestParam(required = false) final String genre) {

        if (allParamsNotNull(title, author, genre) && allParamsNotBlank(title, author, genre)) {
            List<BookProjection> books = bookService.findBooksByRequestParameters(title, author, genre);
            model.addAttribute("books", books);
        }
        return "book_search_result";
    }

    @GetMapping(value = "/library/books/search/quick")
    public String quickSearch(final Model model, @RequestParam(required = false) final String title) {
        if (!title.isBlank()) {
            List<BookProjection> books = bookService.findBooksByTitleRequestParam(title);
            model.addAttribute("books", books);
        }
        return "book_search_result";
    }

    @ModelAttribute("bookDto")
    public BookDto getBookDto() {
        return new BookDto();
    }

    private boolean allParamsNotBlank(final String title, final String author, final String genre) {
        return !title.isBlank() || !author.isBlank() || !genre.isBlank();
    }

    private boolean allParamsNotNull(final String title, final String author, final String genre) {
        return title != null && author != null && genre != null;
    }

}
