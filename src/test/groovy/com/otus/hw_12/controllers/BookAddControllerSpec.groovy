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

import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view

@WebMvcTest(controllers = [BookAddController])
class BookAddControllerSpec extends Specification {

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
    def "add(Model) method returns 200 OK"() {
        expect:
        mockMvc.perform(MockMvcRequestBuilders.get("/library/books/add").contentType(MediaType.TEXT_HTML))
            .andExpect(status().isOk())
            .andExpect(authenticated())
            .andExpect(view().name("book_add_new"))
    }

    def "add(Model) method returns 401 unauthorized"() {
        expect:
        mockMvc.perform(MockMvcRequestBuilders.get("/library/books/add").contentType(MediaType.TEXT_HTML))
            .andExpect(status().isUnauthorized())
            .andExpect(unauthenticated())
    }
}
