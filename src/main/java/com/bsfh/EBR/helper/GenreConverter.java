package com.bsfh.EBR.helper;

import com.bsfh.EBR.model.Genre;
import com.bsfh.EBR.service.DBService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class GenreConverter implements Converter<String, Genre> {
    private final DBService<Genre> service;

    public GenreConverter(DBService<Genre> service) {
        this.service = service;
    }

    @Override
    public Genre convert(String id) {
        return service.find(Genre.class, UUID.fromString(id));
    }
}
