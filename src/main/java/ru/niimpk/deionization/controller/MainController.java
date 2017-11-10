package ru.niimpk.deionization.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import ru.niimpk.deionization.model.counters.StatementCounter;
import ru.niimpk.deionization.model.warehouse.CreateDeleteUtil;
import ru.niimpk.deionization.service.MainService;

import java.text.SimpleDateFormat;

@Controller
public class MainController {

    private static final Logger log = Logger.getLogger(MainController.class);

    @Autowired
    private MainService service;

    @RequestMapping(value = "/")
    public ModelAndView home() {
        ModelAndView mov = new ModelAndView();
        mov.setViewName("home");
        mov.addObject("statement", new StatementCounter());
        mov.addObject("plant", service.getPlant());
        return mov;
    }

    @RequestMapping(value = "/warehouse")
    public ModelAndView warehouse() {
        ModelAndView mov = new ModelAndView();
        mov.setViewName("warehouse");
        mov.addObject("warehouse", service.getWarehouse());
        mov.addObject("create", new CreateDeleteUtil());
        mov.addObject("delete", new CreateDeleteUtil());
        return mov;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/add-to-warehouse")
    public String addToWarehouse(@ModelAttribute CreateDeleteUtil cdu) {
        service.addToDateBase(cdu);
        return "redirect:/warehouse";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/delete-from-warehouse")
    public String deleteFromWarehouse(@ModelAttribute CreateDeleteUtil cdu) {
        service.deleteFiltersFromWarehouse(cdu);
        return "redirect:/warehouse";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/change-statement")
    public String addStatement(@ModelAttribute StatementCounter sc) {
        service.changeStatements(sc);
        return "redirect:/";
    }
}
