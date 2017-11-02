package ru.niimpk.deionization.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.niimpk.deionization.DAO.MainDAO;
import ru.niimpk.deionization.model.Filter;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MainService {

    @Autowired
    private MainDAO dao;
    

    public Map<String, List<Filter>> getFilterMap() {
        Map<String, List<Filter>> map = new HashMap<>();

        return map;
    }
}
