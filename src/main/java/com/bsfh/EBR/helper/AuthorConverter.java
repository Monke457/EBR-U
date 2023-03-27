package com.bsfh.EBR.helper;

import com.bsfh.EBR.model.Author;
import com.bsfh.EBR.service.DBService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class AuthorConverter implements Converter<String, Author> {
    private final DBService<Author> service;

    public AuthorConverter(DBService<Author> service) {
        this.service = service;
    }

    @Override
    public Author convert(String id) {
        return service.find(Author.class, UUID.fromString(id));
    }
}
