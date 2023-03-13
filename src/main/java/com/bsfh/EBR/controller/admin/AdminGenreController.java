package com.bsfh.EBR.controller.admin;

import com.bsfh.EBR.config.AuthUser;
import com.bsfh.EBR.model.Genre;
import com.bsfh.EBR.service.DBService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/admin/genres")
public class AdminGenreController extends AdminController<Genre> {

    public AdminGenreController(AuthUser user, DBService<Genre> service) {
        super(user, service, Genre.class);
    }
}
