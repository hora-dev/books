package com.library.books.repository;

import com.library.books.repository.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
    Optional<Book> findByIsbn(String isbn);
    @Query("select b from Book b where authors like %:authorName%")
    List<Book> findByAuthorName(@Param("authorName") String author);
}
