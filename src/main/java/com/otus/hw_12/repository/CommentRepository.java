package com.otus.hw_12.repository;

import com.otus.hw_12.domain.Book;
import com.otus.hw_12.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    Iterable<Comment> findCommentsByBook(Book book);

    Iterable<Comment> findByCommentaryContainingIgnoreCase(String text);

}
