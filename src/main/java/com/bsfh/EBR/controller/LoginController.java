package com.bsfh.EBR.controller;

import com.bsfh.EBR.model.Customer;
import com.bsfh.EBR.model.helper.RegistrationData;
import com.bsfh.EBR.service.DBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.HashSet;
import java.util.Objects;

@Controller
public class LoginController {

    @Autowired
    private DBService<Customer> customerService;
    @Autowired
    private PasswordEncoder encoder;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String showLogin(Model model) {
        model.addAttribute("formData", new Customer());
        return "login";
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String showRegistration(Model model) {
        model.addAttribute("formData", new RegistrationData());
        return "register";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String register(Model model, @ModelAttribute RegistrationData formData, BindingResult bindingResult) {
        String errorMessage = null;

        if (customerService.findByUniqueAttribute(Customer.class, "username", formData.getUsername()) != null) {
            errorMessage = "Username already taken.";
        } else if (!Objects.equals(formData.getPassword(), formData.getPasswordRepeat())) {
            errorMessage = "Passwords do not match.";
        }

        if (errorMessage != null) {
            model.addAttribute("formData", new RegistrationData(formData.getUsername(), formData.getEmail(), "", ""));
            model.addAttribute("error", errorMessage);
            return "register";
        }

        customerService.create(new Customer(formData.getUsername(), encoder.encode(formData.getPassword()), formData.getEmail(), false, new HashSet<>()));
        model.addAttribute("formData", new Customer());
        return "login";
    }
}
