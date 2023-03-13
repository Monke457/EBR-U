package com.bsfh.EBR.controller.admin;

import com.bsfh.EBR.config.AuthUser;
import com.bsfh.EBR.helper.Template;
import com.bsfh.EBR.model.DBEntity;
import com.bsfh.EBR.model.helper.SearchData;
import com.bsfh.EBR.service.DBService;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public abstract class AdminController<T extends DBEntity> {

    private AuthUser user;
    protected DBService<T> service;
    private Class<T> type;
    private String template;
    private String redirect;

    public AdminController(AuthUser user, DBService<T> service, Class<T> type) {
        this.user = user;
        this.service = service;
        this.type = type;

        if(type.isAnnotationPresent(Template.class)) {
            this.template = type.getAnnotation(Template.class).admin();
        }

        if(getClass().isAnnotationPresent(RequestMapping.class)) {
            this.redirect = "redirect:" + getClass().getAnnotation(RequestMapping.class).value()[0];
        }
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String table(Model model) {
        addGlobalAttributes(model);

        List<T> results = service.findAll(type).collect(Collectors.toList());
        model.addAttribute("results", results);
        model.addAttribute("formData", null);

        return template;
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String add(Model model) {
        addGlobalAttributes(model);

        try {
            model.addAttribute("formData", type.getDeclaredConstructor().newInstance());

        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException | InstantiationException ex) {
            ex.printStackTrace();
        }
        return template;
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String add(@ModelAttribute T item, BindingResult bindingResult) {
        if(!bindingResult.hasErrors()) {
            service.create(item);
        }
        return redirect;
    }

    @RequestMapping(value = "/{id}/edit", method = RequestMethod.GET)
    protected String edit(Model model, @PathVariable UUID id) {
        addGlobalAttributes(model);

        T entity = service.find(type, id);
        model.addAttribute("formData", entity);

        return template;
    }

    @RequestMapping(value = "/{id}/edit", method = RequestMethod.POST)
    protected String edit(@ModelAttribute T item, BindingResult bindingResult) {
        if(!bindingResult.hasErrors()) {
            service.update(item);
        }
        return redirect;
    }

    @RequestMapping(value = "/{id}/delete", method = RequestMethod.POST)
    protected String remove(@PathVariable UUID id) {
        service.delete(type, id);
        return redirect;
    }

    private void addGlobalAttributes(Model model) {
        model.addAttribute("user", user.getUser());
        model.addAttribute("searchData", new SearchData());
    }
}
