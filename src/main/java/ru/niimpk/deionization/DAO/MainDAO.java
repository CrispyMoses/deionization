package ru.niimpk.deionization.DAO;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.niimpk.deionization.model.Filter;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

@Repository
public class MainDAO {

    @Autowired
    private EntityManager em;

    public List<Filter> getWarehouseFilters() {
        List<Filter> list = new ArrayList<>();
        return list;
    }
}
