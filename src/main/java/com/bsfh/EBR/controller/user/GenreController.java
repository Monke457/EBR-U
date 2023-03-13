package com.bsfh.EBR.controller.user;

import com.bsfh.EBR.config.AuthUser;
import com.bsfh.EBR.model.Book;
import com.bsfh.EBR.model.Genre;
import com.bsfh.EBR.model.helper.FilterData;
import com.bsfh.EBR.service.DBService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.HashSet;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Controller
@RequestMapping(value = "/genres")
public class GenreController extends AbstractController<Genre> {

    private DBService<Book> bookService;
    private DBService<Genre> genreService;

    public GenreController(AuthUser user, DBService<Genre> service, DBService<Book> bookService) {
        super(user, service, Genre.class, "name");

        this.bookService = bookService;
        this.genreService = service;
    }

    @Override
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String findAll(Model model) {
        List<Book> books = bookService.findAll(Book.class).collect(Collectors.toList());

        model.addAttribute("filterData", new FilterData());
        model.addAttribute("books", books);
        return super.findAll(model);
    }

    @Override
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String find(@PathVariable UUID id, Model model) {
        super.addGlobalAttributes(model);

        List<Genre> genres = genreService.findAll(Genre.class, "name").collect(Collectors.toList());
        Genre genre = genreService.find(Genre.class, id);
        List<Book> books = bookService.findByRelation(Book.class, "genres", genre.getId(), 0, "title").collect(Collectors.toList());

        model.addAttribute("results", genres);
        model.addAttribute("filterData", new FilterData(new HashSet<>(List.of(genre.getId()))));
        model.addAttribute("books", books);

        return "genres";
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public String filter(Model model, @ModelAttribute FilterData filterData, BindingResult bindingResult) {
        super.addGlobalAttributes(model);

        List<Genre> genres = genreService.findAll(Genre.class, "name").collect(Collectors.toList());
        List<Book> books;

        if(filterData.getGenreIds().isEmpty()) {
            books = bookService.findAll(Book.class, "title").collect(Collectors.toList());

        } else {
            books = bookService.findByRelation(Book.class, "genres", filterData.getGenreIds(), 0, "title").collect(Collectors.toList());
        }

        model.addAttribute("results", genres);
        model.addAttribute("filterData", filterData);
        model.addAttribute("books", books);

        return "genres";
    }
}