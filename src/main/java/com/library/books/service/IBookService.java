package com.library.books.service;


import com.library.books.repository.entity.Book;
import com.library.books.service.dto.BookDTO;

import java.util.List;

public interface IBookService {
    BookDTO addBook(Book book);
    BookDTO getBook(String isbn);
    List<BookDTO> getAllBooks();
    void deleteBook(String isbn);
    void deleteAllBooks();

    boolean updateBook(String isbn, Book book);
    List<BookDTO> getByAuthorName(String authorName);
}
