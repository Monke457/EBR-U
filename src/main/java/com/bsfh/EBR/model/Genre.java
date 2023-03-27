package com.bsfh.EBR.model;

import com.bsfh.EBR.helper.Template;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Template(user = "genres", admin = "admin-genres")
public class Genre implements DBEntity {
    @Id
    @GeneratedValue
    private UUID id;
    private String name;

    @ManyToMany(mappedBy = "genres")
    private Set<Book> books = new HashSet<>();

    public Genre(String name, Set<Book> books) {
        this.name = name;
        this.books = books;
    }

    @Override
    public String toString() {
        return name;
    }
}
