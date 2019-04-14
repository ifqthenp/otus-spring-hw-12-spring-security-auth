package com.otus.hw_12.domain

import spock.lang.Specification

class GenreSpec extends Specification {

    def "no-args constructor sets attributes to default values"() {
        given:
        Genre genre = new Genre()
        assert genre != null

        expect:
        with(genre) {
            id == 0
            genreName == null
            books != null
            books.getClass() == HashSet
        }
    }

}
