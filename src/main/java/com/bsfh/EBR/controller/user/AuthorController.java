package com.bsfh.EBR.controller.user;

import com.bsfh.EBR.config.AuthUser;
import com.bsfh.EBR.model.Author;
import com.bsfh.EBR.service.DBService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/authors")
public class AuthorController extends AbstractController<Author>{

    public AuthorController(AuthUser user, DBService<Author> service) {
        super(user, service, Author.class, "lastName");
    }
}
