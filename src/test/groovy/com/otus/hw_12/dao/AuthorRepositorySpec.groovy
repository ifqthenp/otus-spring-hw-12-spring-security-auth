package com.otus.hw_12.dao

import com.otus.hw_12.domain.Author
import com.otus.hw_12.repository.AuthorRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.annotation.DirtiesContext
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

@DataJpaTest
class AuthorRepositorySpec extends Specification {

    @Subject
    @Autowired
    AuthorRepository authorRepo

    void setup() {
        assert authorRepo != null
    }

    def "can count authors in the table"() {
        expect:
        authorRepo.count() == 10
    }

    def "can find author by id"() {
        given:
        def id = 4L
        Optional<Author> author = authorRepo.findById(id)

        expect:
        author.isPresent()
        with(author.get()) {
            getId() == id
            getFirstName() == 'Herbert'
            getLastName() == 'Wells'
        }
    }

    @Unroll
    def "can find author by the #name, case insensitive"() {
        when:
        def authors = authorRepo.findByAuthorName(name)

        then:
        authors.size() == result

        where:
        name     || result
        'rBErt'  || 1
        'WeL'    || 1
        'leo'    || 2
        'oliver' || 0
    }

    def "can find all authors"() {
        given:
        Iterable<Author> authors = authorRepo.findAll()

        expect:
        authors.size() == 10
    }

    @DirtiesContext
    def "can save an author"() {
        given:
        def first = 'Georg'
        def last = 'Hegel'
        Author hegel = new Author(firstName: first, lastName: last)

        when:
        hegel = authorRepo.save(hegel)

        then:
        with(hegel) {
            id == 11
            firstName == 'Georg'
            lastName == 'Hegel'
        }
    }

    @DirtiesContext
    def "can save a collection of authors"() {
        given:
        def newAuthors = [['Charles', 'Dickens'], ['Virginia', 'Woolf']].collect {
            it -> new Author(firstName: it[0], lastName: it[1])
        }

        when:
        authorRepo.saveAll(newAuthors)

        then:
        with(newAuthors.last()) {
            id == 12
            lastName == 'Woolf'
        }
    }

    @DirtiesContext
    def "can delete an author"() {
        given:
        def id = 4L
        def allAuthors = authorRepo.findAll()
        Optional<Author> author = authorRepo.findById(id)

        when:
        author.ifPresent({ authorRepo.delete(author.get()) })

        and:
        author = authorRepo.findById(id)
        allAuthors = authorRepo.findAll()

        then:
        author.isEmpty()
        allAuthors.size() == old(allAuthors.size()) - 1
    }

    void cleanup() {
        authorRepo = null
    }
}
