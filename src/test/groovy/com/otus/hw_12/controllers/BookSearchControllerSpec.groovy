package com.otus.hw_12.controllers

import com.otus.hw_12.auth.LibraryUserDetailsService
import com.otus.hw_12.services.BookService
import org.spockframework.spring.SpringBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import spock.lang.Specification

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view

@WebMvcTest(BookSearchController)
class BookSearchControllerSpec extends Specification {

    @Autowired
    MockMvc mockMvc

    @SpringBean
    BookService bookService = Mock()

    @SpringBean
    LibraryUserDetailsService userDetailsService = Mock()

    void setup() {
        assert mockMvc != null
        assert bookService != null
    }

    @WithMockUser
    def "fullSearch(Model, String, String, String) returns 200 response"() {
        expect:
        mockMvc.perform(MockMvcRequestBuilders.get("/library/books/search/full"))
            .andExpect(status().isOk())
            .andExpect(view().name("book_search_result"))
    }

    @WithMockUser
    def "quickSearch(String, Model) returns 200 response"() {
        expect:
        mockMvc.perform(MockMvcRequestBuilders.get("/library/books/search/quick?title="))
            .andExpect(status().isOk())
            .andExpect(view().name("book_search_result"))
    }

    def "quickSearch(String, Model) returns 401 unauthorized"() {
        expect:
        mockMvc.perform(MockMvcRequestBuilders.get("/library/books/search/quick?title=")
            .contentType(MediaType.TEXT_HTML))
            .andExpect(status().isUnauthorized())
    }
}
