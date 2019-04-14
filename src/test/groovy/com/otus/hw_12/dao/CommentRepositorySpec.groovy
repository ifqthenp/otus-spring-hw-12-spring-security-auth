package com.otus.hw_12.dao

import com.otus.hw_12.domain.Comment
import com.otus.hw_12.repository.BookRepository
import com.otus.hw_12.repository.CommentRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.annotation.DirtiesContext
import spock.lang.Specification
import spock.lang.Subject

@DataJpaTest
class CommentRepositorySpec extends Specification {

    @Subject
    @Autowired
    CommentRepository commentRepo

    @Autowired
    BookRepository bookRepo

    void setup() {
        assert bookRepo != null
        assert commentRepo != null
    }

    def "can count comments in the table"() {
        expect:
        commentRepo.count() == 5
    }

    def "can find a comment by its id"() {
        given:
        def commentId = 2L

        when:
        Optional<Comment> comment = commentRepo.findById(commentId)

        then:
        with(comment) {
            isPresent()
            get().id == 2
            get().commentary == 'nice'
            get().book.title == 'Jane Eyre'
            get().book.comments.first().commentary == 'nice'
        }
    }

    def "can find all comments in the database"() {
        given:
        def comments = commentRepo.findAll()

        expect:
        comments.size() == 5
    }

    def "can find comments by book id"() {
        given:
        def one = bookRepo.findById(1L)
        def two = bookRepo.findById(2L)

        and:
        def commentsForOne = commentRepo.findCommentsByBook(one.get())
        def commentsForTwo = commentRepo.findCommentsByBook(two.get())

        expect:
        commentsForOne.size() == 1
        commentsForTwo.size() == 2
        commentsForTwo.last().commentary == 'awesome book'
    }

    def "can find a comment by text search, case insensitive"() {
        given:
        def text = 'Good'

        and:
        def goodComments = commentRepo.findByCommentaryContainingIgnoreCase(text)

        expect:
        goodComments.size() == 2
        goodComments.first().commentary == 'pretty good'
        goodComments.last().commentary == 'very good'
    }

    @DirtiesContext
    def "can save a comment"() {
        given:
        def out = 'outstanding'
        def mar = 'marvellous'

        and:
        def allComments = commentRepo.findAll()

        when:
        Comment outstanding = commentRepo.save(new Comment(commentary: out))
        Comment marvellous = commentRepo.save(new Comment(commentary: mar))

        then:
        outstanding.id == allComments.size() + 1
        outstanding.commentary == out
        outstanding.book == null

        marvellous.id == allComments.size() + 2
        marvellous.commentary == mar
        marvellous.book == null
    }

    @DirtiesContext
    def "can save a collection of comments"() {
        given:
        Iterable<Comment> comments = commentRepo.findAll()

        and:
        def newComments = ['very nice!', 'beautiful story'].collect {
            new Comment(commentary: it)
        }

        when:
        commentRepo.saveAll(newComments)

        and:
        comments = commentRepo.findAll()

        then:
        comments.last().commentary == 'beautiful story'
        comments.size() == old(comments.size()) + newComments.size()
    }

    @DirtiesContext
    def "can delete a comment"() {
        given:
        def id = 2L
        def allComments = commentRepo.findAll()
        Optional<Comment> comment = commentRepo.findById(id)

        when:
        comment.ifPresent({ commentRepo.delete(comment.get()) })

        and:
        comment = commentRepo.findById(id)
        allComments = commentRepo.findAll()

        then:
        comment.isEmpty()
        allComments.size() == old(allComments.size()) - 1
    }

    void cleanup() {
        bookRepo = null
        commentRepo = null
    }
}
