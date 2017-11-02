package ru.niimpk.deionization.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.niimpk.deionization.DAO.MainDAO;

import java.text.SimpleDateFormat;

@Service
public class MainService {

    @Autowired
    private MainDAO dao;

    public String getDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");
        return dateFormat.format(dao.getFilter().getPurchaseDate());
    }
}
