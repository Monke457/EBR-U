package com.bsfh.EBR.helper;

import com.bsfh.EBR.model.Customer;
import com.bsfh.EBR.service.DBService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CustomerConverter implements Converter<String, Customer> {
    private DBService<Customer> service;

    public CustomerConverter(DBService<Customer> service) {
        this.service = service;
    }

    @Override
    public Customer convert(String id) {
        return service.findByUniqueAttribute(Customer.class, "id", id);
    }
}
