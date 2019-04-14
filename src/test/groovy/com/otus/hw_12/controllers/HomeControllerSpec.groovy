package com.otus.hw_12.controllers

import com.otus.hw_12.auth.LibraryUserDetailsService
import com.otus.hw_12.services.AuthorService
import com.otus.hw_12.services.BookService
import org.spockframework.spring.SpringBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import spock.lang.Specification

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@WebMvcTest(controllers = [HomeController])
class HomeControllerSpec extends Specification {

    @Autowired
    MockMvc mockMvc

    @SpringBean
    BookService bookService = Mock()

    @SpringBean
    AuthorService authorService = Mock()

    @SpringBean
    LibraryUserDetailsService libraryUserDetailsService = Mock()

    void setup() {
        assert mockMvc != null
        assert bookService != null
        assert authorService != null
    }

    def "index(Model) returns 200 status"() {
        expect:
        mockMvc.perform(MockMvcRequestBuilders.get("/home"))
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
            .andReturn()
            .getResponse()
            .getContentAsString()
            .contains("Welcome to our library!")
    }

    def "redirect() method returns correct code"() {
        expect:
        mockMvc.perform(MockMvcRequestBuilders.get("/"))
            .andExpect(status().is3xxRedirection())
            .andExpect(view().name("redirect:/home"))
    }
}
