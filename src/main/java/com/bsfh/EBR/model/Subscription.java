package com.bsfh.EBR.model;


import com.bsfh.EBR.helper.Template;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Template(user = "subscriptions", admin = "admin-subscriptions")
public class Subscription implements DBEntity {
    @Id
    @GeneratedValue
    private UUID id;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOpened;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateClosed;

    @ManyToOne
    private Customer customer;

    @ManyToOne
    private Book book;

    public Subscription(LocalDate dateOpened, LocalDate dateClosed, Customer customer, Book book) {
        this.dateOpened = dateOpened;
        this.dateClosed = dateClosed;
        this.customer = customer;
        this.book = book;
    }

    @Override
    public String toString() {
        return getId() + ". " + dateOpened + ": " + customer + ", " + book;
    }
}
