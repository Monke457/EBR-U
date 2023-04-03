package com.bsfh.EBR.controller.user;

import com.bsfh.EBR.config.AuthUser;
import com.bsfh.EBR.model.Subscription;
import com.bsfh.EBR.service.DBService;
import nl.siegmann.epublib.domain.Book;
import nl.siegmann.epublib.domain.Resource;
import nl.siegmann.epublib.epub.EpubReader;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Blob;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping(value = "/subscriptions")
public class SubscriptionController extends AbstractController<Subscription> {

    private final DBService<Subscription> service;

    public SubscriptionController(AuthUser user, DBService<Subscription> service) {
        super(user, service, Subscription.class, "dateClosed", "dateOpened");

        this.service = service;
    }

    @Override
    @RequestMapping(method = RequestMethod.GET)
    public String findAll(Model model) {
        addGlobalAttributes(model);

        List<Subscription> results = service.findByRelation(Subscription.class, "customer", user.getUser().getId(), 0, "dateClosed", "dateOpened").collect(Collectors.toList());
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
        Subscription sub = service.find(Subscription.class, id);

        if (sub.getDateClosed() != null && !sub.getDateClosed().isAfter(LocalDate.now())) {
            return "redirect:/subscriptions";
        }

        addGlobalAttributes(model);
        model.addAttribute("resources", null);

        Blob contentAsBlob = sub.getBook().getContent();
        try {
            InputStream stream = contentAsBlob.getBinaryStream();

            EpubReader epubReader = new EpubReader();
            Book epub = epubReader.readEpub(stream);

            List<String> resources = new ArrayList<>();

            for (Resource resource : epub.getContents()) {
                resources.add(new String(resource.getData(), StandardCharsets.UTF_8));
            }

            model.addAttribute("resources", resources);


        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }

        return "reader";
    }
}
