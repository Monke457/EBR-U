package com.bsfh.EBR.controller.user;

import com.bsfh.EBR.config.AuthUser;
import com.bsfh.EBR.helper.Template;
import com.bsfh.EBR.model.DBEntity;
import com.bsfh.EBR.model.helper.SearchData;
import com.bsfh.EBR.service.DBService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public abstract class AbstractController<T extends DBEntity> {

    protected AuthUser user;
    private final DBService<T> service;
    private final Class<T> type;
    private final String[] sortFields;
    private String template = "";

    public AbstractController(AuthUser user, DBService<T> service, Class<T> type, String... sortFields) {
        this.user = user;
        this.service = service;
        this.type = type;
        this.sortFields = sortFields;

        if(type.isAnnotationPresent(Template.class)) {
            template = type.getAnnotation(Template.class).user();
        }
    }

    @RequestMapping(method = RequestMethod.GET)
    public String findAll(Model model) {
        addGlobalAttributes(model);

        List<T> results = service.findAll(type, sortFields).collect(Collectors.toList());
        model.addAttribute("results", results);

        return template;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String find(@PathVariable UUID id, Model model) {
        addGlobalAttributes(model);

        T result = service.find(type, id);
        model.addAttribute("result", result);

        return template;
    }

    protected void addGlobalAttributes(Model model) {
        model.addAttribute("searchData", new SearchData());
        model.addAttribute("user", user.getUser());
    }
}
