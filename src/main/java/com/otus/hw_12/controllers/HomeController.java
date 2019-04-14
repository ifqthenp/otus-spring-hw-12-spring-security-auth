package com.otus.hw_12.controllers;

import com.otus.hw_12.services.AuthorService;
import com.otus.hw_12.services.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final BookService bookService;
    private final AuthorService authorService;

    @GetMapping("/home")
    public String index(final Model model) {
        model.addAttribute("booksTotal", bookService.getCount());
        model.addAttribute("authorsTotal", authorService.getCount());
        return "index";
    }

    @GetMapping("/")
    public String redirect() {
        return "redirect:/home";
    }

}
