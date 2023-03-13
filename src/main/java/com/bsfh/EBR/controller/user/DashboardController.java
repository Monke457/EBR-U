package com.bsfh.EBR.controller.user;

import com.bsfh.EBR.config.AuthUser;
import com.bsfh.EBR.model.Book;
import com.bsfh.EBR.model.Genre;
import com.bsfh.EBR.model.helper.SearchData;
import com.bsfh.EBR.model.Subscription;
import com.bsfh.EBR.service.DBQuery;
import com.bsfh.EBR.service.DBService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Controller
public class DashboardController {

    private AuthUser user;

    private DBService<Book> bookService;
    private DBService<Genre> genreService;
    private DBService<Subscription> subService;

    public DashboardController(AuthUser user, DBService<Book> bookService, DBService<Genre> genreService, DBService<Subscription> subService) {
        this.user = user;

        this.bookService = bookService;
        this.genreService = genreService;
        this.subService = subService;
    }

    @RequestMapping(value = "/")
    public String index(Model model) {
        books(model);
        subs(model);
        genres(model);

        model.addAttribute("user", user.getUser());
        model.addAttribute("searchData", new SearchData());

        return "index";
    }

    public void books(Model model) {
        List<Book> books = bookService.find(Book.class, new DBQuery(8)).collect(Collectors.toList());
        model.addAttribute("books", books);
    }

    public void subs(Model model) {
        List<Subscription> subs = null;
        if(user.getUser() != null) {
            List<Subscription> subList = subService.findByRelation(Subscription.class, "customer", user.getUser().getId(), 0, "dateOpened")
                    .filter(sub -> sub.getDateClosed() == null)
                    .limit(8)
                    .collect(Collectors.toList());

            if(!subList.isEmpty()) {
                subs = subList;
            }
        }
        model.addAttribute("subs", subs);
    }

    public void genres(Model model) {
        List<Genre> genres1 = new ArrayList<>();
        List<Genre> genres2 = new ArrayList<>();
        List<Genre> genres3 = new ArrayList<>();

        List<Genre> genres = genreService.find(Genre.class, new DBQuery(18)).toList();

        for(int i = 0; i < genres.size(); i++) {
            switch (i) {
                case 0, 1, 2, 3, 4, 5 -> genres1.add(genres.get(i));
                case 6, 7, 8, 9, 10, 11 -> genres2.add(genres.get(i));
                case 12, 13, 14, 15, 16, 17 -> genres3.add(genres.get(i));
            }
        }

        model.addAttribute("genres", Arrays.asList(genres1, genres2, genres3));
    }
}
