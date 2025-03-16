package com.library.books.service.dto;

import lombok.Data;

@Data
public class BookDTO {
    private String bookTitle, authors, publisher, isbn;
    private int yearPublished, price;
}
