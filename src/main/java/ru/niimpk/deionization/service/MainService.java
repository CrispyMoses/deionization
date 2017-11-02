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

    public String getDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");
        return dateFormat.format(dao.getFilter().getPurchaseDate());
    }

    public Map<String, List<Filter>> getFilterMap() {
        Map<String, List<Filter>> map = new HashMap<>();

        return map;
    }
}
