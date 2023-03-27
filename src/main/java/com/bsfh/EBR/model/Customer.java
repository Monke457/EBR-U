package com.bsfh.EBR.model;

import com.bsfh.EBR.helper.Template;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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
@Template(admin = "admin-customers")
public class Customer implements DBEntity {
    @Id
    @GeneratedValue
    private UUID id;
    private String username;
    private String password;
    private String email;
    private boolean admin;
    @OneToMany(mappedBy = "customer")
    private Set<Subscription> subscriptions = new HashSet<>();

    public Customer(String username, String password, String email, boolean admin, Set<Subscription> subscriptions) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.admin = admin;
        this.subscriptions = subscriptions;
    }

    @Override
    public String toString() {
        return getId() + ": " + username;
    }
}