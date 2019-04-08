package com.otus.hw_12.controllers;

import com.otus.hw_12.domain.Book;
import com.otus.hw_12.dto.BookDto;
import com.otus.hw_12.services.BookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Slf4j
@Controller
@RequiredArgsConstructor
public class BookAddController {

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

    @ModelAttribute("bookDto")
    public BookDto getBookDto() {
        return new BookDto();
    }

}
