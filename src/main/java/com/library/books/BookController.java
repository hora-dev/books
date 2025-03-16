package com.library.books;

import com.library.books.repository.entity.Book;
import com.library.books.service.IBookService;
import com.library.books.service.dto.BookDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
@Slf4j
public class BookController {

    private final IBookService iBookService;

    @PostMapping
    public ResponseEntity<BookDTO> addBook(@RequestBody Book book, UriComponentsBuilder uriComponentsBuilder) {
        log.warn("XXX book is {}", book);
        BookDTO bookDTO = iBookService.addBook(book);

        URI locationURI = uriComponentsBuilder
                .path("/books/" + bookDTO.getIsbn())
                .buildAndExpand(uriComponentsBuilder.toUriString())
                .toUri();
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .location(locationURI)
                .body(bookDTO);
    }

    @GetMapping("/{isbn}")
    public ResponseEntity<BookDTO> getBook(@PathVariable String isbn) {
        return ResponseEntity.ok(iBookService.getBook(isbn));
    }

    @GetMapping
    public ResponseEntity<List<BookDTO>> getAllBooks(){
        return ResponseEntity.ok(iBookService.getAllBooks());
    }

    @DeleteMapping("/{isbn}")
    public ResponseEntity<Void> deleteBookByIsbn(@PathVariable String isbn) {
        iBookService.deleteBook(isbn);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteAllBooks() {
        iBookService.deleteAllBooks();
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{isbn}")
    public ResponseEntity<BookDTO> updateBook(@PathVariable String isbn, @RequestBody Book book) {
        iBookService.updateBook(isbn, book);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/book")
    public ResponseEntity<List<BookDTO>> getByAuthor(@RequestParam String author) {
        return ResponseEntity.ok(iBookService.getByAuthorName(author));
    }

    @PostMapping("/{isbn}")
    public ResponseEntity<Void> postOnIndividualResourceNotSupported() {
        return ResponseEntity
                .status(HttpStatus.METHOD_NOT_ALLOWED)
                .build();
    }

    @PutMapping
    public ResponseEntity<Void> putOnCollectionResourceNotSupported() {
        return ResponseEntity
                .status(HttpStatus.METHOD_NOT_ALLOWED)
                .build();
    }

    @RequestMapping(method = RequestMethod.OPTIONS)
    public ResponseEntity<Void> options() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .allow(HttpMethod.HEAD, HttpMethod.GET, HttpMethod.POST, HttpMethod.DELETE)
                .build();
    }

    @RequestMapping(method = RequestMethod.OPTIONS, path = "/{isbn}")
    public ResponseEntity<Void> optionsOnIndividualResource() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .allow(HttpMethod.HEAD, HttpMethod.GET, HttpMethod.PUT, HttpMethod.DELETE)
                .build();
    }

}
