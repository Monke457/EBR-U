package com.bsfh.EBR.controller.user;

import com.bsfh.EBR.config.AuthUser;
import com.bsfh.EBR.model.Author;
import com.bsfh.EBR.model.Book;
import com.bsfh.EBR.model.helper.SearchData;
import com.bsfh.EBR.service.DBQuery;
import com.bsfh.EBR.service.DBService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping(value = "/search")
public class SearchController {

    private AuthUser user;
    private DBService<Book> bookService;
    private DBService<Author> authorService;

    public SearchController(AuthUser user, DBService<Book> bookService, DBService<Author> authorService) {
        this.user = user;
        this.bookService = bookService;
        this.authorService = authorService;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String search(Model model) {
        model.addAttribute("searchData", new SearchData());
        model.addAttribute("user", user.getUser());
        model.addAttribute("books", new ArrayList<>());
        model.addAttribute("authors", new ArrayList<>());

        return "search";
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public String search(Model model, @ModelAttribute SearchData searchData, BindingResult bindingResult) {
        List<Book> books = bookService.find(Book.class, new DBQuery(searchData.getTerm())).collect(Collectors.toList());
        List<Author> authors = authorService.find(Author.class, new DBQuery(searchData.getTerm(), false)).collect(Collectors.toList());

        model.addAttribute("books", books);
        model.addAttribute("authors", authors);
        model.addAttribute("user", user.getUser());

        return "search";
    }
}
