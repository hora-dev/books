package com.library.books.service.impl;

import com.library.books.controller.exception.BookISBNAlreadyExistsException;
import com.library.books.controller.exception.BookISBNMismatchException;
import com.library.books.controller.exception.BookISBNNotFoundException;
import com.library.books.repository.BookRepository;
import com.library.books.repository.entity.Book;
import com.library.books.service.IBookService;
import com.library.books.service.dto.BookDTO;
import com.library.books.service.dto.BookMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements IBookService {

    private final BookRepository bookRepository;

    @Override
    public BookDTO addBook(Book book) {
        bookRepository.findByIsbn(book.getIsbn()).ifPresent(b -> {
            throw new BookISBNAlreadyExistsException("That book already exists in db! : " + book.getIsbn());
        });
        Book savedBook = bookRepository.save(book);
        BookDTO bookDTO = new BookDTO();
        return BookMapper.mapToBookDTO(savedBook, bookDTO);
    }

    @Override
    public BookDTO getBook(String isbn) {
        return BookMapper.mapToBookDTO(
                bookRepository.findByIsbn(isbn)
                        .orElseThrow(() -> new BookISBNNotFoundException("Book ISBN not found in db! : " + isbn)), new BookDTO());
    }

    @Override
    public List<BookDTO> getAllBooks() {
        return bookRepository.findAll().stream()
                .map(book -> BookMapper.mapToBookDTO(book, new BookDTO()))
                .toList();
    }

    @Override
    public void deleteBook(String isbn) {
        Book book = bookRepository
                .findByIsbn(isbn)
                .orElseThrow(() -> new BookISBNNotFoundException("Book ISBN not found in db! : " + isbn));
        bookRepository.delete(book);
    }

    @Override
    public void deleteAllBooks() {
        bookRepository.findAll().forEach(bookRepository::delete);
    }

    @Override
    public boolean updateBook(String isbn, Book book) {
        if(!book.getIsbn().equalsIgnoreCase(isbn)) throw new BookISBNMismatchException("ISBN numbers mismatch! URI: " + isbn + ", Entity Body: " + book.getIsbn());
        Book b = bookRepository.findByIsbn(isbn).orElseThrow( () -> new BookISBNNotFoundException("Book ISBN not found in db! : " + isbn));
        b.setBookTitle(book.getBookTitle());
        b.setAuthors(book.getAuthors());
        b.setPublisher(book.getPublisher());
        b.setIsbn(book.getIsbn());
        b.setYearPublished(book.getYearPublished());
        b.setPrice(book.getPrice());
        bookRepository.save(b);
        return true;
    }

    @Override
    public List<BookDTO> getByAuthorName(String authorName) {
        return bookRepository.findByAuthorName(authorName)
                .stream()
                .map(book -> BookMapper.mapToBookDTO(book, new BookDTO()))
                .toList();
    }
}
