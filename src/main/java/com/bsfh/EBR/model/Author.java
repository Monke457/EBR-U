package com.bsfh.EBR.model;

import com.bsfh.EBR.helper.Template;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Template(user = "authors", admin = "admin-authors")
public class Author implements DBEntity {
    @Id
    @GeneratedValue
    private UUID id;
    private String firstName;
    private String lastName;
    @OneToMany(mappedBy = "author")
    private Set<Book> books;

    public Author(String firstName, String lastName, Set<Book> books) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.books = books;
    }

    @Override
    public String toString() {
        return firstName + " " + lastName;
    }
}
