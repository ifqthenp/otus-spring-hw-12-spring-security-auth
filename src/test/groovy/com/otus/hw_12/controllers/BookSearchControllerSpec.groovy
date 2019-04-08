package com.otus.hw_12.controllers

import com.otus.hw_12.services.BookService
import org.spockframework.spring.SpringBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import spock.lang.Specification

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view

@AutoConfigureMockMvc(secure = false)
@WebMvcTest(BookSearchController)
class BookSearchControllerSpec extends Specification {

    @Autowired
    MockMvc mockMvc

    @SpringBean
    BookService bookService = Mock()

    void setup() {
        assert mockMvc != null
        assert bookService != null
    }

    def "fullSearch(Model, String, String, String) returns 200 response"() {
        expect:
        mockMvc.perform(MockMvcRequestBuilders.get("/library/books/search/full"))
            .andExpect(status().isOk())
            .andExpect(view().name("book_search_result"))
    }

    def "quickSearch(String, Model) returns 200 response"() {
        expect:
        mockMvc.perform(MockMvcRequestBuilders.get("/library/books/search/quick?title="))
            .andExpect(status().isOk())
            .andExpect(view().name("book_search_result"))
    }
}
