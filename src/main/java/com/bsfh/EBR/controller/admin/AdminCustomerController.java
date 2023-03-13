package com.bsfh.EBR.controller.admin;

import com.bsfh.EBR.config.AuthUser;
import com.bsfh.EBR.model.Customer;
import com.bsfh.EBR.service.DBService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/admin/customers")
public class AdminCustomerController extends AdminController<Customer> {

    private PasswordEncoder encoder;

    public AdminCustomerController(AuthUser user, DBService<Customer> service, PasswordEncoder encoder) {
        super(user, service, Customer.class);
        this.encoder = encoder;
    }

    @Override
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String add(@ModelAttribute Customer customer, BindingResult bindingResult) {
        customer.setPassword(encoder.encode(customer.getUsername()));
        return super.add(customer, bindingResult);
    }
}
