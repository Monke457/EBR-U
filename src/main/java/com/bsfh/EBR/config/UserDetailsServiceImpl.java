package com.bsfh.EBR.config;

import com.bsfh.EBR.model.Customer;
import com.bsfh.EBR.service.DBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private DBService<Customer> service;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Customer customer = service.findByUniqueAttribute(Customer.class, "username", username);

        List<GrantedAuthority> auths = new ArrayList<>();

        if(customer.isAdmin()) {
            auths.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        }

        return new User(customer.getUsername(), customer.getPassword(), auths);
    }
}
