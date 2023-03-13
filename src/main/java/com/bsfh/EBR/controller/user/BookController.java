package com.bsfh.EBR.controller.user;

import com.bsfh.EBR.config.AuthUser;
import com.bsfh.EBR.model.BlobFile;
import com.bsfh.EBR.model.Book;
import com.bsfh.EBR.model.DBEntity;
import com.bsfh.EBR.model.Subscription;
import com.bsfh.EBR.model.helper.SearchData;
import com.bsfh.EBR.service.DBService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Controller
@RequestMapping(value = "/books")
public class BookController extends AbstractController<Book> {

    private DBService<Subscription> subService;
    private DBService<Book> bookService;
    private DBService<BlobFile> blobService;

    public BookController(AuthUser user, DBService<Book> bookService, DBService<BlobFile> blobService, DBService<Subscription> subService) {
        super(user, bookService, Book.class, "title");

        this.bookService = bookService;
        this.blobService = blobService;
        this.subService = subService;
    }

    @Override
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String find(@PathVariable UUID id, Model model) {
        Subscription subscription = null;

        if(user.getUser() != null) {
            List<Subscription> subs = subService.findByRelation(Subscription.class, "customer", user.getUser().getId(), 0, "dateOpened")
                    .filter(sub -> sub.getDateClosed() == null).toList();

            for(Subscription sub : subs) {
                if(Objects.equals(sub.getBook().getId(), id)) {
                    subscription = sub;
                    break;
                }
            }
        }

        addSimilarBooks(model, bookService.find(Book.class, id));
        model.addAttribute("subscription", subscription);

        return super.find(id, model);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public String subscribe(Model model, @PathVariable UUID id, @RequestParam(value="submit", required=true) String action) {

        Book book = bookService.find(Book.class, id);
        Subscription subscription = null;

        if(user.getUser() != null) {
            if(action.equals("Unsub")) {
                List<Subscription> subs = subService.findByRelation(Subscription.class, "customer", user.getUser().getId(), 0, "dateOpened").collect(Collectors.toList());

                for(Subscription sub : subs) {
                    if(sub.getBook().getId() == id) {
                        sub.setDateClosed(LocalDate.now());
                        subService.update(sub);
                        break;
                    }
                }

            } else if(action.equals("Subscribe")) {
                subscription = new Subscription(LocalDate.now(), null, user.getUser(), book);
                subService.create(subscription);
            }
        }

        addSimilarBooks(model, book);
        model.addAttribute("result", book);
        model.addAttribute("subscription", subscription);
        model.addAttribute("searchData", new SearchData());
        model.addAttribute("user", user.getUser());

        return "books";
    }

    @RequestMapping(value = "/{id}/similar", method = RequestMethod.GET)
    public String similar(Model model, @PathVariable UUID id) {
        Book book = bookService.find(Book.class, id);
        List<Book> books = bookService.findByRelation(Book.class, "genres", book.getGenres().stream().map(DBEntity::getId).collect(Collectors.toSet()), 0, "title").collect(Collectors.toList());
        books.removeIf(b -> b.getId() == id);

        model.addAttribute("result", book);
        model.addAttribute("similar", books);
        model.addAttribute("searchData", new SearchData());
        model.addAttribute("user", user.getUser());

        return "books";
    }

    private void addSimilarBooks(Model model, Book book) {
        List<Book> booksByGenre = bookService.findByRelation(Book.class, "genres", book.getGenres().stream().map(DBEntity::getId).collect(Collectors.toSet()), 4, "title").collect(Collectors.toList());
        List<Book> booksByAuthor = bookService.findByRelation(Book.class, "author", book.getAuthor().getId(), 4, "title").collect(Collectors.toList());

        booksByGenre.removeIf(b -> b.getId() == book.getId());
        booksByAuthor.removeIf(b -> b.getId() == book.getId());

        model.addAttribute("booksByGenre", booksByGenre);
        model.addAttribute("booksByAuthor", booksByAuthor);
    }

    @GetMapping("/{id}/cover")
    public void showImage(@PathVariable("id") UUID id, HttpServletResponse response) {
        try {
            Book book = bookService.find(Book.class, id);

            if(book.getCover() == null) {
                InputStream stream = getClass().getResourceAsStream("/static/img/icon-m.png");
                if(stream != null) {
                    byte[] fileAsBytes = stream.readAllBytes();
                    response.setContentType("image/png");
                    response.getOutputStream().write(fileAsBytes);
                }

            } else {
                Blob blob = book.getCover().getFileData();
                int blobLength = (int) blob.length();
                byte[] blobAsBytes = blob.getBytes(1, blobLength);
                blob.free();

                response.setContentType(book.getCover().getMime());
                response.getOutputStream().write(blobAsBytes);
            }
            response.getOutputStream().close();

        } catch (IOException | SQLException ex) {
            ex.printStackTrace();
        }
    }
}
