package com.bsfh.EBR.controller.admin;

import com.bsfh.EBR.config.AuthUser;
import com.bsfh.EBR.model.Author;
import com.bsfh.EBR.model.Book;
import com.bsfh.EBR.model.Genre;
import com.bsfh.EBR.service.DBService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Controller
@RequestMapping(value = "/admin/books")
public class AdminBookController extends AdminController<Book> {

    private final DBService<Author> authorService;
    private final DBService<Genre> genreService;

    public AdminBookController(AuthUser user, DBService<Book> service, DBService<Author> authorService, DBService<Genre> genreService) {
        super(user, service, Book.class);

        this.authorService = authorService;
        this.genreService = genreService;
    }

    @Override
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String add(Model model) {
        addModelAttributes(model);
        return super.add(model);
    }

    @Override
    @RequestMapping(value = "/{id}/edit", method = RequestMethod.GET)
    public String edit(Model model, @PathVariable UUID id) {
        addModelAttributes(model);
        return super.edit(model, id);
    }

    //Hack to get around the current image being deleted when nothing is uploaded in the edit
    @Override
    @RequestMapping(value = "/{id}/edit", method = RequestMethod.POST)
    protected String edit(@ModelAttribute Book item, BindingResult bindingResult) {
        Book bookFromBackend = service.find(Book.class, item.getId());
        if (item.getCover() == null) {
            item.setCover(bookFromBackend.getCover());
        }
        if (item.getContent() == null) {
            item.setContent(bookFromBackend.getContent());
        }
        return super.edit(item, bindingResult);
    }

    private void addModelAttributes(Model model) {
        List<Author> authors = authorService.findAll(Author.class).collect(Collectors.toList());
        List<Genre> genres = genreService.findAll(Genre.class).collect(Collectors.toList());
        model.addAttribute("authors", authors);
        model.addAttribute("genres", genres);
    }
}
