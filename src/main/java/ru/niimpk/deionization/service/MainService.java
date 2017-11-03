package ru.niimpk.deionization.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.niimpk.deionization.DAO.MainDAO;
import ru.niimpk.deionization.model.filters.Filter;
import ru.niimpk.deionization.model.filters.FilterName;
import ru.niimpk.deionization.model.warehouse.CreateDeleteUtil;
import ru.niimpk.deionization.model.warehouse.Warehouse;

@Transactional
@Service
public class MainService {

    private static final Logger log = Logger.getLogger(MainService.class);

    @Autowired
    private MainDAO dao;
    

    public Warehouse getWarehouse() {
        Warehouse warehouse = new Warehouse();
        warehouse.setINamount(dao.getWarehouseFiltersAmount(FilterName.IN));
        warehouse.setPFamount(dao.getWarehouseFiltersAmount(FilterName.PF));
        warehouse.setDKamount(dao.getWarehouseFiltersAmount(FilterName.DK));
        warehouse.setA13amount(dao.getWarehouseFiltersAmount(FilterName.A13));
        warehouse.setKFamount(dao.getWarehouseFiltersAmount(FilterName.KF));
        warehouse.setAFamount(dao.getWarehouseFiltersAmount(FilterName.AF));
        warehouse.setFSDamount(dao.getWarehouseFiltersAmount(FilterName.FSD));
        return warehouse;
    }

    public void addToDateBase(CreateDeleteUtil cdu) {
        for (int i = 0; i < cdu.getAmount(); i++) {
            Filter filter = new Filter();
            filter.setName(cdu.getName());
            filter.setFullName("залупка");
            filter.setPurchaseDate(cdu.getDate());
            dao.persistFilter(filter);
        }
    }
}
