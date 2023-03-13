package com.bsfh.EBR.model;

import com.bsfh.EBR.helper.Template;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UuidGenerator;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Blob;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Template(user = "books", admin = "admin-books")
public class Book implements DBEntity{
    @Id
    @GeneratedValue
    private UUID id;
    private String title;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate publicationDate;

    @Column(length = 2000)
    private String synopsis;
    private int pageCount;
    private float pricePerDay;
    private String isbn;
    private float rating;

    @OneToOne
    private BlobFile cover;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private Author author;

    @ManyToMany
    private Set<Genre> genres;

    @Lob
    private Blob content;

    public Book(String title, LocalDate publicationDate, String synopsis, int pageCount, float pricePerDay, String isbn, float rating, BlobFile cover, Author author, Set<Genre> genres, Blob content) {
        this.title = title;
        this.publicationDate = publicationDate;
        this.synopsis = synopsis;
        this.pageCount = pageCount;
        this.pricePerDay = pricePerDay;
        this.isbn = isbn;
        this.rating = rating;
        this.cover = cover;
        this.author = author;
        this.genres = genres;
        this.content = content;
    }

    @Override
    public String toString() {
        return title;
    }
}