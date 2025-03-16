package com.library.books.repository.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // jakarta - use a db id column
    private int id;

    @Column(name = "book_title")
    private String bookTitle;

    private String authors;
    private String publisher;
    private String isbn;

    @Column(name = "year_published")
    private int yearPublished;

    private int price;
}
