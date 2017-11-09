package ru.niimpk.deionization.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.niimpk.deionization.DAO.MainDAO;
import ru.niimpk.deionization.model.condition.*;
import ru.niimpk.deionization.model.counters.StatementCounter;
import ru.niimpk.deionization.model.filters.Filter;
import ru.niimpk.deionization.model.filters.FilterFullName;
import ru.niimpk.deionization.model.filters.FilterLocation;
import ru.niimpk.deionization.model.filters.FilterName;
import ru.niimpk.deionization.model.warehouse.CreateDeleteUtil;
import ru.niimpk.deionization.model.warehouse.Warehouse;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Transactional
@Service
public class MainService {

    private static final Logger logger = Logger.getLogger(MainService.class);

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
            filter.setPurchaseDate(cdu.getDate());
            filter.setLocation(FilterLocation.WAREHOUSE);
            dao.persistFilter(filter);
        }
    }


    public void deleteFiltersFromWarehouse(CreateDeleteUtil cdu) {
        for (int i = 0; i < cdu.getAmount(); i++) {
            dao.deleteFilter(dao.getOlderFilter(cdu.getName(), FilterLocation.WAREHOUSE));
        }
    }


    //Зафикисровать показания счётчика во всех рабочих фильтрах
    public void changeStatements(StatementCounter sc) {
        dao.persistStatement(sc);
        changeInStatement(dao.getWorkFilter(PlantMappingName.IN1), sc.getIncomeStatement());
        changeInStatement(dao.getWorkFilter(PlantMappingName.IN2), sc.getIncomeStatement());
        changeInStatement(dao.getWorkFilter(PlantMappingName.PF), sc.getIncomeStatement());
        changeInStatement(dao.getWorkFilter(PlantMappingName.DK), sc.getIncomeStatement());
        changeInStatement(dao.getWorkFilter(PlantMappingName.A13), sc.getIncomeStatement());
        changeInStatement(dao.getWorkFilter(PlantMappingName.KF), sc.getOutStatement());
        changeInStatement(dao.getWorkFilter(PlantMappingName.AF), sc.getOutStatement());
        changeInStatement(dao.getWorkFilter(PlantMappingName.FSD), sc.getOutStatement());
        changeInStatement(dao.getWorkFilter(PlantMappingName.FSDr), sc.getOutStatement());
    }



    //TODO: Фсд регенерации всё ещё не работает нормально
    public List<PartOfPlant> getPlant() {
        List<PartOfPlant> plant = new LinkedList<>();
        plant.add(createPoP(PlantMappingName.IN1, FilterFullName.IN));
        plant.add(createPoP(PlantMappingName.IN2, FilterFullName.IN));
        plant.add(createPoP(PlantMappingName.PF, FilterFullName.PF));
        plant.add(createPoP(PlantMappingName.DK, FilterFullName.DK));
        plant.add(createPoP(PlantMappingName.A13, FilterFullName.A13));
        plant.add(createPoP(PlantMappingName.KF, FilterFullName.KF));
        plant.add(createPoP(PlantMappingName.AF, FilterFullName.AF));
        plant.add(createPoP(PlantMappingName.FSD, FilterFullName.FSD));
        Reservoir reservoir = dao.getReservoir();
        plant.add(new PartOfPlant("Накопительная ёмкость", 80, reservoir.getLastRegeneration()));
        plant.add(createPoP(PlantMappingName.FSDr, FilterFullName.FSD));
        return plant;
    }

    private void changeInStatement(Filter filter, int change) {
        filter.setPassedWaterVolume(filter.getPassedWaterVolume() + change);
        dao.persistFilter(filter);
    }

    private PartOfPlant createPoP(PlantMappingName name, String fullName) {
        PartOfPlant pop = new PartOfPlant();
        Filter filter = dao.getWorkFilter(name);
        pop.setFilter(filter);
        pop.setFullName(fullName);
        if (filter.getName().equals(FilterName.A13)) pop.setLastRegeneration(filter.getLastRegeneration());
        pop.setWearPercentage(getWearPercentage(filter));
        return pop;
    }

    //TODO: поправить расчёт (дело в типах инт и флоат)
    private int getWearPercentage(Filter filter) {
        long today = new Date().getTime();
        switch (filter.getName()) {
            case IN: return filter.getPassedWaterVolume()/DataLimit.IN * 100;
            case PF: return filter.getPassedWaterVolume()/DataLimit.PF * 100;
            case DK: return (int) ((today - filter.getInstallationDate().getTime())/DataLimit.DK * 100);
            case A13: return (int) ((today - filter.getPassedWaterVolume())/DataLimit.A13 * 100);
            case KF: return filter.getPassedWaterVolume()/DataLimit.KF * 100;
            case AF: return filter.getPassedWaterVolume()/DataLimit.AF * 100;
            case FSD: return filter.getPassedWaterVolume()/DataLimit.FSD * 100;
            default: return 0;
        }
    }
}
