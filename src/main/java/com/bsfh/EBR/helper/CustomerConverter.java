package com.bsfh.EBR.helper;

import com.bsfh.EBR.model.Customer;
import com.bsfh.EBR.service.DBService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class CustomerConverter implements Converter<String, Customer> {
    private final DBService<Customer> service;

    public CustomerConverter(DBService<Customer> service) {
        this.service = service;
    }

    @Override
    public Customer convert(String id) {
        return service.find(Customer.class, UUID.fromString(id));
    }
}
