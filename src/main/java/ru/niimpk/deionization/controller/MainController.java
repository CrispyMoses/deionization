package ru.niimpk.deionization.controller;

import org.apache.log4j.Logger;
import org.hibernate.validator.constraints.URL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.niimpk.deionization.model.condition.PlantMappingName;
import ru.niimpk.deionization.model.counters.StatementCounter;
import ru.niimpk.deionization.model.counters.StatementDateLimit;
import ru.niimpk.deionization.model.filters.FilterName;
import ru.niimpk.deionization.model.warehouse.CreateDeleteUtil;
import ru.niimpk.deionization.service.MainService;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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

    @RequestMapping(value = "/replace/{plantMappingName}/{filterName}")
    public String replaceFilter(@PathVariable("plantMappingName") String plantMappingName, @PathVariable("filterName") String filterName) {
        try {
            if (!(filterName.equals("null")))
                service.replaceFilter(PlantMappingName.valueOf(plantMappingName), FilterName.valueOf(filterName));
            else service.replaceFilter(PlantMappingName.valueOf(plantMappingName), null);
        } catch (IndexOutOfBoundsException e) {return "redirect:/";}
        return "redirect:/";
    }


    @RequestMapping(value = "/statistic")
    public ModelAndView statistic(@ModelAttribute StatementDateLimit sdl) {
        ModelAndView mov = new ModelAndView("statistic");
        mov.addObject("dateLimit", new StatementDateLimit());
        if (sdl.getAfter() != null) {
            List<StatementCounter> list = service.getStatementList(sdl);
            mov.addObject("discharge", service.getDischarge(list));
            mov.addObject("statements", list);
        }
        return mov;
    }
}
