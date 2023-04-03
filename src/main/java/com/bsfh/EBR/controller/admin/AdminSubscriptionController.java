package com.bsfh.EBR.controller.admin;

import com.bsfh.EBR.config.AuthUser;
import com.bsfh.EBR.model.Book;
import com.bsfh.EBR.model.Customer;
import com.bsfh.EBR.model.Subscription;
import com.bsfh.EBR.service.DBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Controller
@RequestMapping(value = "/admin/subscriptions")
public class AdminSubscriptionController extends AdminController<Subscription> {

    private final DBService<Customer> customerService;
    private final DBService<Book> bookService;

    public AdminSubscriptionController(AuthUser user, DBService<Subscription> service, DBService<Customer> customerService, DBService<Book> bookService) {
        super(user, service, Subscription.class);

        this.customerService = customerService;
        this.bookService = bookService;
    }

    @Override
    @RequestMapping(value = "/{id}/edit", method = RequestMethod.GET)
    public String edit(Model model, @PathVariable UUID id) {
        List<Customer> customers = customerService.findAll(Customer.class).collect(Collectors.toList());
        List<Book> books = bookService.findAll(Book.class).collect(Collectors.toList());

        model.addAttribute("customers", customers);
        model.addAttribute("books", books);

        return super.edit(model, id);
    }
}