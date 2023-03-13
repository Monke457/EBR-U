package com.bsfh.EBR.controller.admin;

import com.bsfh.EBR.config.AuthUser;
import com.bsfh.EBR.model.Author;
import com.bsfh.EBR.service.DBService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/admin/authors")
public class AdminAuthorController extends AdminController<Author> {

    public AdminAuthorController(AuthUser user, DBService<Author> service) {
        super(user, service, Author.class);
    }
}
