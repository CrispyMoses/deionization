package ru.niimpk.deionization.DAO;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.niimpk.deionization.model.Filter;

import javax.persistence.EntityManager;

@Repository
public class MainDAO {

    @Autowired
    private EntityManager em;

    public Filter getFilter() {
        return em.find(Filter.class, new Integer(1));
    }
}
