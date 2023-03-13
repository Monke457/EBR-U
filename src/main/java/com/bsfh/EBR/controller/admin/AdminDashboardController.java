package com.bsfh.EBR.controller.admin;

import com.bsfh.EBR.config.AuthUser;
import com.bsfh.EBR.model.helper.SearchData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AdminDashboardController {

    private AuthUser user;

    public AdminDashboardController(AuthUser user) {
        this.user = user;
    }

    @RequestMapping(value = "/admin")
    public String index(Model model) {
        model.addAttribute("user", user.getUser());
        model.addAttribute("searchData", new SearchData());

        return "admin";
    }
}
