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
@WebMvcTest(controllers = [BookAddController])
class BookAddControllerSpec extends Specification {

    @Autowired
    MockMvc mockMvc

    @SpringBean
    BookService bookService = Mock()

    void setup() {
        assert mockMvc != null
        assert bookService != null
    }

    def "add(Model) method returns 200 response"() {
        expect:
        mockMvc.perform(MockMvcRequestBuilders.get("/library/books/add"))
            .andExpect(status().isOk())
            .andExpect(view().name("book_add_new"))
    }
}
