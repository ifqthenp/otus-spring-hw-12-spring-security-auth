package com.otus.hw_12.dao

import com.otus.hw_12.domain.Genre
import com.otus.hw_12.repository.BookRepository
import com.otus.hw_12.repository.GenreRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.annotation.DirtiesContext
import spock.lang.Specification
import spock.lang.Subject

@DataJpaTest
class GenreRepositorySpec extends Specification {

    @Subject
    @Autowired
    GenreRepository genreRepo

    @Autowired
    BookRepository bookRepo

    void setup() {
        assert bookRepo != null
        assert genreRepo != null
    }

    def "can find genre by id"() {
        given:
        Optional<Genre> genre = genreRepo.findById(2L)

        expect:
        genre.isPresent()
        with(genre.get()) {
            id == 2
            genreName == 'Fantasy'
            !books.isEmpty()
            books.size() == 1
            books.first().id == 1
        }
    }

    def "can find genre by its name, case insensitive"() {
        given:
        Optional<Genre> fantasyGenre = genreRepo.findByGenreNameContainingIgnoreCase('fant')

        expect:
        fantasyGenre.isPresent()
        fantasyGenre.get().genreName == 'Fantasy'
    }

    def "can find all genres"() {
        given:
        Iterable<Genre> genres = genreRepo.findAll()

        expect:
        genres.size() > 0
        genres.first().genreName == 'Literary realism'
        genres.last().genreName == 'Satire'
    }

    def "can count genres in the table"() {
        expect:
        genreRepo.count() == 9
    }

    @DirtiesContext
    def "can save a genre"() {
        given:
        Genre genre = new Genre()
        genre.genreName = 'Horror'
        Genre savedGenre = genreRepo.save(genre)

        and:
        Optional<Genre> foundGenre = genreRepo.findByGenreNameContainingIgnoreCase(genre.genreName)

        expect:
        foundGenre.isPresent()
        foundGenre.get() == savedGenre
    }

    @DirtiesContext
    def "can save a collection of genres"() {
        given:
        Iterable<Genre> existingGenres = genreRepo.findAll()

        and:
        Iterable<Genre> newGenres = ['Drama', 'Comedy'].collect {
            new Genre(genreName: it)
        }

        when:
        genreRepo.saveAll(newGenres)

        and:
        existingGenres = genreRepo.findAll()

        then:
        existingGenres.size() == old(existingGenres.size()) + newGenres.size()
        existingGenres.last() == genreRepo.findById(existingGenres.size() as Long).get()
    }

    @DirtiesContext
    def "can delete a genre"() {
        given:
        def genreName = 'novel'
        Optional<Genre> genre = genreRepo.findByGenreNameContainingIgnoreCase(genreName)

        and:
        genre.ifPresent({ genreRepo.delete(it) })

        expect:
        bookRepo.findBookByGenreName(genre.get().genreName).isEmpty()
        genreRepo.findByGenreNameContainingIgnoreCase(genreName).isEmpty()
    }

    void cleanup() {
        bookRepo = null
        genreRepo = null
    }
}
