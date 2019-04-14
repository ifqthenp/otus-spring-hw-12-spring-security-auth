package com.otus.hw_12.domain

import spock.lang.Specification

class CommentSpec extends Specification {

    def "no-args constructor sets attributes to default values"() {
        given:
        Comment comment = new Comment()
        assert comment != null

        expect:
        with(comment) {
            id == 0
            commentary == null
            book == null
        }
    }

}
