package ru.niimpk.deionization.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import ru.niimpk.deionization.service.MainService;

@Controller
public class MainController {

    @Autowired
    private MainService service;

    @RequestMapping(value = "/")
    public ModelAndView home() {
        ModelAndView mov = new ModelAndView();
        mov.setViewName("home");
        return mov;
    }

    @RequestMapping(value = "/warehouse")
    public ModelAndView warehouse() {
        ModelAndView mov = new ModelAndView();
        mov.setViewName("warehouse");
        return mov;
    }
}
