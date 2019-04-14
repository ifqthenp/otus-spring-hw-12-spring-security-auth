package com.otus.hw_12.domain

import spock.lang.Specification

class AuthorSpec extends Specification {

    def "no-args constructor sets attributes to default values"() {
        given:
        Author author = new Author()
        assert author != null

        expect:
        with(author) {
            id == 0
            firstName == null
            lastName == null
            books != null
            books.getClass() == HashSet
        }
    }

}
