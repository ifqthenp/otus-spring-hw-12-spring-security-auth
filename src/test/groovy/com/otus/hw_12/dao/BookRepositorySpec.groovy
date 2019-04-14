package com.otus.hw_12.dao

import com.otus.hw_12.domain.Author
import com.otus.hw_12.domain.Book
import com.otus.hw_12.domain.Comment
import com.otus.hw_12.domain.Genre
import com.otus.hw_12.repository.AuthorRepository
import com.otus.hw_12.repository.BookRepository
import com.otus.hw_12.repository.CommentRepository
import com.otus.hw_12.repository.GenreRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.annotation.DirtiesContext
import spock.lang.Specification
import spock.lang.Subject

@DataJpaTest
class BookRepositorySpec extends Specification {

    @Subject
    @Autowired
    BookRepository bookRepo

    @Autowired
    AuthorRepository authorRepo

    @Autowired
    CommentRepository commentRepo

    @Autowired
    GenreRepository genreRepo

    void setup() {
        assert bookRepo != null
        assert genreRepo != null
        assert authorRepo != null
        assert commentRepo != null
    }

    def "can find book by id"() {
        given:
        def bookId = 2L

        and:
        Optional<Book> book = bookRepo.findById(bookId)

        expect:
        book.isPresent()
        with(book.get()) {
            id == bookId
            title == 'Jane Eyre'
            year == '1847'
        }
    }

    def "can find books by title, case insensitive"() {
        given:
        def books = bookRepo.findByTitleContainingIgnoreCase(title)

        expect:
        books.size() == sizeRes
        books.every {
            it.title.toLowerCase().contains(title)
        }

        where:
        title   || sizeRes
        'child' || 1
        'time'  || 2
    }

    def "can find all books"() {
        given:
        Iterable<Book> books = bookRepo.findAll()

        expect:
        books.size() == 12
        with(books.last()) {
            id == 12
            title == 'The Little Golden Calf'
            year == '1931'
            genres.genreName == ['Satire', 'Novel']
            authors.lastName == ['Petrov', 'Ilf']
        }
    }

    def "can count books in the table"() {
        expect:
        bookRepo.count() == 12
    }

    @DirtiesContext
    def "can save a book"() {
        given:
        def t = 'Test book'
        def y = '2000'
        Book book = new Book(title: t, year: y)

        when:
        book = bookRepo.save(book)
        def allBooks = bookRepo.findAll()

        then:
        with(book) {
            id == allBooks.size()
            title == t
            year == y
            comments == []
            authors == [] as Set
        }

        and:
        allBooks.size() == 13
    }

    @DirtiesContext
    def "can save a collection of books"() {
        given:
        Iterable<Book> allBooks = bookRepo.findAll()

        and:
        def newBooks = [['War and Peace', '1869'], ['The Invisible Man', '1933']].collect {
            new Book(title: it[0], year: it[1])
        }

        when:
        newBooks = bookRepo.saveAll(newBooks)

        and:
        allBooks = bookRepo.findAll()

        then:
        allBooks.size() == old(allBooks.size()) + newBooks.size()
        newBooks.each { it.id > allBooks.size() }
        allBooks.last().title == 'The Invisible Man'
    }

    @DirtiesContext
    def "can delete a book"() {
        given:
        def bookId = 3L
        Optional<Book> book = bookRepo.findById(bookId)

        when:
        book.ifPresent({ bookRepo.delete(book.get()) })

        then:
        bookRepo.findById(bookId) == Optional.empty()
    }

    def "can find books by author"() {
        given:
        def leo = 'leo'
        def emptyTest = 'test'

        when:
        Iterable<Book> leoBooks = bookRepo.findBookByAuthorName(leo)
        Iterable<Book> emptyBooks = bookRepo.findBookByAuthorName(emptyTest)

        then:
        emptyBooks.isEmpty()
        leoBooks.size() == 4
        leoBooks.first().title == 'Anna Karenina'
    }

    def "can find books by genre, case insensitive"() {
        when:
        def books = bookRepo.findBookByGenreName(genre)

        then:
        books.size() == sizeResult
        if (sizeResult > 0) books.first().title == titleResult

        where:
        genre     || sizeResult || titleResult
        'realism' || 3          || 'Anna Karenina'
        'fant'    || 1          || 'Alice in Wonderland'
        'none'    || 0          || _
    }

    @DirtiesContext
    def "can add a comment to a book"() {
        given:
        def bookId = 1L
        def text = 'The best book ever'
        def comments = commentRepo.findAll()
        Book book = bookRepo.findById(bookId).get()
        Comment comment = new Comment(commentary: text)

        when:
        book.addComment(comment)
        bookRepo.save(book)

        and:
        comments = commentRepo.findAll()

        then:
        comments.size() == old(comments.size()) + 1
        bookRepo.findById(bookId).get().comments.last().commentary == text
    }

    @DirtiesContext
    def "can delete a comment from a book"() {
        given:
        def bookId = 2L
        def aBook = bookRepo.findById(bookId).get()
        def uselessComment = aBook.comments.first()
        def allCommentsInDatabase = commentRepo.findAll()

        when:
        aBook.removeComment(uselessComment)
        aBook = bookRepo.save(aBook)

        and:
        allCommentsInDatabase = commentRepo.findAll()

        then:
        aBook.comments.size() == old(aBook.comments.size()) - 1
        allCommentsInDatabase.size() == old(allCommentsInDatabase.size()) - 1
    }

    @DirtiesContext
    def "can add a genre to the book"() {
        given:
        def bookId = 1L
        def book = bookRepo.findById(bookId).get()
        def genre = genreRepo.findById(4L).get()
        def genresInBook = book.genres.size()

        when:
        book.addGenre(genre)

        and:
        genresInBook = book.genres.size()

        then:
        genresInBook == old(genresInBook) + 1
        book.genres.last().genreName == 'Novel'
    }

    @DirtiesContext
    def "can add a new genre to a database by adding it to the book"() {
        given:
        def bookId = 1L
        def book = bookRepo.findById(bookId).get()

        and:
        def horror = 'Horror'
        def genre = new Genre(genreName: horror)

        and:
        def genresInBook = book.genres.size()
        def genresInDatabase = genreRepo.findAll().size()

        when:
        book.addGenre(genre)

        and:
        genresInBook = book.genres.size()
        genresInDatabase = genreRepo.findAll().size()

        then:
        genresInBook == old(genresInBook) + 1
        book.genres.last().genreName == horror

        and:
        genresInDatabase == old(genresInDatabase) + 1
        genreRepo.findAll().last().genreName == horror
    }

    @DirtiesContext
    def "removing genre from the book does not remove it from database"() {
        given:
        def bookId = 4L
        def book = bookRepo.findById(bookId).get()
        def genreId = 5L
        def genreToRemove = genreRepo.findById(genreId).get()

        and:
        def genresInBook = book.genres
        def genresInDatabase = genreRepo.findAll()

        when:
        book.removeGenre(genreToRemove)

        and:
        genresInBook == book.genres
        genresInDatabase = genreRepo.findAll()

        then:
        book.genres.size() == old(book.genres.size()) - 1
        genresInDatabase.size() == old(genresInDatabase.size())
    }

    @DirtiesContext
    def "adding new author to the book will add it to the database"() {
        given:
        def bookId = 1L
        def book = bookRepo.findById(bookId).get()

        and:
        def author = new Author(firstName: 'John', lastName: 'Doe')

        and:
        def authorsInBook = book.authors
        def authorsInDatabase = authorRepo.findAll()

        when:
        book.addAuthor(author)

        and:
        authorsInBook = book.authors
        authorsInDatabase = authorRepo.findAll()

        then:
        authorsInBook.size() == old(authorsInBook.size()) + 1
        authorsInDatabase.size() == old(authorsInDatabase.size()) + 1
        book.authors.any() { it =~ /Doe/ }
    }

    @DirtiesContext
    def "removing author from the book will not remove it from the database"() {
        given:
        def authorId = 3L
        def author = authorRepo.findById(authorId).get()

        and:
        def bookId = 3L
        def book = bookRepo.findById(bookId).get()

        and:
        def authorsInBook = book.authors
        def authorsInDatabase = authorRepo.findAll()

        when:
        book.removeAuthor(author)

        and:
        authorsInBook = book.authors
        authorsInDatabase = authorRepo.findAll()

        then:
        authorsInBook.size() == 0
        authorsInDatabase.size() == old(authorsInDatabase.size())
    }

    void cleanup() {
        bookRepo = null
        genreRepo = null
        authorRepo = null
        commentRepo = null
    }
}
