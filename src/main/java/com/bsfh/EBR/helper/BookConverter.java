package com.bsfh.EBR.helper;

import com.bsfh.EBR.model.Book;
import com.bsfh.EBR.service.DBService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class BookConverter implements Converter<String, Book> {
    private final DBService<Book> bookService;

    public BookConverter(DBService<Book> service) {
        this.bookService = service;
    }

    @Override
    public Book convert(String id) {
        return bookService.find(Book.class, UUID.fromString(id));
    }
}
