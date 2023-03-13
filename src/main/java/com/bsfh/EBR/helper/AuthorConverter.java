package com.bsfh.EBR.helper;

import com.bsfh.EBR.model.Author;
import com.bsfh.EBR.service.DBService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class AuthorConverter implements Converter<String, Author> {
    private DBService<Author> service;

    public AuthorConverter(DBService<Author> service) {
        this.service = service;
    }

    @Override
    public Author convert(String id) {
        return service.findByUniqueAttribute(Author.class, "id", id);
    }
}
