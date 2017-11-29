package ru.niimpk.deionization.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.niimpk.deionization.model.condition.PlantMappingName;
import ru.niimpk.deionization.model.condition.UtilizedHelper;
import ru.niimpk.deionization.model.counters.StatementCounter;
import ru.niimpk.deionization.model.counters.StatementDateLimit;
import ru.niimpk.deionization.model.counters.WrongStatementException;
import ru.niimpk.deionization.model.filters.FilterName;
import ru.niimpk.deionization.model.warehouse.CreateDeleteUtil;
import ru.niimpk.deionization.service.MainService;

import java.util.List;

@Controller
public class MainController {

    private static final Logger log = Logger.getLogger(MainController.class);

    @Autowired
    private MainService service;

    @RequestMapping(value = "/login")
    public String login() {return "login";}

    @RequestMapping(value = "/")
    public ModelAndView home(@ModelAttribute("wrongStatement") String error, @ModelAttribute("success") String success) {
        log.info(error);
        ModelAndView mov = new ModelAndView();
        mov.setViewName("home");
        if (!error.equals("")) mov.addObject("wrongStatement", error);
        mov.addObject("statement", new StatementCounter());
        mov.addObject("plant", service.getPartsOfPlant());
        if (!success.equals(""))
            mov.addObject("success", success);
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
    public ModelAndView addStatement(@ModelAttribute StatementCounter sc) {
        try {
            service.changeStatements(sc);
        } catch (WrongStatementException e) {
            ModelAndView mav = new ModelAndView("redirect:/");
            mav.addObject("wrongStatement", "Вы ввели значение меньше предыдущего");
            return mav;
        }
        ModelAndView mav = new ModelAndView("redirect:/");
        mav.addObject("success", "Показания успешно сохранены");
        return mav;
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
    public ModelAndView statistic(@ModelAttribute("dateLimit") StatementDateLimit sdl, @ModelAttribute("utilizedHelper") UtilizedHelper uh) {
        ModelAndView mov = new ModelAndView("statistic");
        mov.addObject("dateLimit", new StatementDateLimit());
        mov.addObject("utilizedHelper", new UtilizedHelper());
        if (sdl.getAfter() != null) {
            List<StatementCounter> list = service.getStatementList(sdl);
            mov.addObject("discharge", service.getDischarge(list));
            mov.addObject("warehouse", service.getWarehouse());
            mov.addObject("statements", list);
        }
//
        if (uh.getuAfter() != null && uh.getuBefore() != null && !uh.getFilterName().equals("null")) {
            mov.addObject("filters",service.getUtilizedFilters(uh));
        }
        return mov;
    }
}
