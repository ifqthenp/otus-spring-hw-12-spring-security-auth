package com.otus.hw_12.domain

import spock.lang.Specification

class BookSpec extends Specification {

    def "no-args constructor sets attributes to default values"() {
        given:
        Book book = new Book()

        expect:
        with(book) {
            id == 0
            title == null
            year == null
            authors != null
            authors.getClass() == HashSet
            genres != null
            genres.getClass() == HashSet
            comments != null
            comments.getClass() == ArrayList
        }
    }

}
