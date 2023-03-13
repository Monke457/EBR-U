package com.bsfh.EBR.config;

import com.bsfh.EBR.model.Customer;
import com.bsfh.EBR.service.DBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthUser {

    @Autowired
    private DBService<Customer> service;

    public Customer getUser() {
        return service.findByUniqueAttribute(Customer.class, "username", SecurityContextHolder.getContext().getAuthentication().getName());
    }
}
