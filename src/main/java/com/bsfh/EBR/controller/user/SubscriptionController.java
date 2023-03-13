package com.bsfh.EBR.controller.user;

import com.bsfh.EBR.config.AuthUser;
import com.bsfh.EBR.model.Subscription;
import com.bsfh.EBR.service.DBService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Controller
@RequestMapping(value = "/subscriptions")
public class SubscriptionController extends AbstractController<Subscription> {

    private DBService<Subscription> service;

    public SubscriptionController(AuthUser user, DBService<Subscription> service) {
        super(user, service, Subscription.class, "dateOpened");

        this.service = service;
    }

    @Override
    @RequestMapping(method = RequestMethod.GET)
    public String findAll(Model model) {
        addGlobalAttributes(model);

        List<Subscription> results = service.findByRelation(Subscription.class, "customer", user.getUser().getId(), 0, "dateOpened")
                .filter(sub -> sub.getDateClosed() == null).collect(Collectors.toList());
        model.addAttribute("results", results);

        return "subscriptions";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public String unsubscribe(Model model, @PathVariable UUID id) {
        addGlobalAttributes(model);

        Subscription sub = service.find(Subscription.class, id);
        sub.setDateClosed(LocalDate.now());
        service.update(sub);

        return "redirect:/subscriptions";
    }

    @GetMapping("/{id}/read")
    public String read(Model model, @PathVariable("id") UUID id) {
        addGlobalAttributes(model);
        Subscription sub = service.find(Subscription.class, id);

        if(sub.getCustomer().getId() == user.getUser().getId()) {
            model.addAttribute("content", sub.getBook().getContent());
            return "reader";
        }

        return "redirect:/subscriptions";
    }
}
