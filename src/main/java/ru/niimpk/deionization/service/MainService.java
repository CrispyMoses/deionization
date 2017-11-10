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

import java.text.SimpleDateFormat;
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
        StatementCounter latestSC = dao.getLastStatement();
        dao.persistStatement(sc);
        changeStatement(dao.getWorkFilter(PlantMappingName.IN1), sc.getIncomeStatement() - latestSC.getIncomeStatement());
        changeStatement(dao.getWorkFilter(PlantMappingName.IN2), sc.getIncomeStatement() - latestSC.getIncomeStatement());
        changeStatement(dao.getWorkFilter(PlantMappingName.PF), sc.getIncomeStatement() - latestSC.getIncomeStatement());
        changeStatement(dao.getWorkFilter(PlantMappingName.DK), sc.getIncomeStatement() - latestSC.getIncomeStatement());
        changeStatement(dao.getWorkFilter(PlantMappingName.A13), sc.getIncomeStatement() - latestSC.getIncomeStatement());
        changeStatement(dao.getWorkFilter(PlantMappingName.KF), sc.getOutStatement() - latestSC.getOutStatement());
        changeStatement(dao.getWorkFilter(PlantMappingName.AF), sc.getOutStatement() - latestSC.getOutStatement());
        changeStatement(dao.getWorkFilter(PlantMappingName.FSD), sc.getOutStatement() - latestSC.getOutStatement());
        changeStatement(dao.getWorkFilter(PlantMappingName.FSDr), sc.getOutStatement() - latestSC.getOutStatement());
    }



    //TODO: Фсд регенерации всё ещё не работает нормально
    public List<PartOfPlant> getPlant() {
        long today = new Date().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy");
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
        PartOfPlant pop = new PartOfPlant("Накопительная ёмкость",
                (int) ((float) ((today - reservoir.getLastRegeneration().getTime()))/DataLimit.E * 100),
                df.format(reservoir.getLastRegeneration()));
        pop.setLastRegeneration(df.format(reservoir.getLastRegeneration()));
        plant.add(pop);
        plant.add(createPoP(PlantMappingName.FSDr, FilterFullName.FSD));
        return plant;
    }

    private void changeStatement(Filter filter, int change) {
        filter.setPassedWaterVolume(filter.getPassedWaterVolume() + change);
        dao.persistFilter(filter);
    }

    private PartOfPlant createPoP(PlantMappingName name, String fullName) {
        SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy");
        PartOfPlant pop = new PartOfPlant();
        Filter filter = dao.getWorkFilter(name);
        pop.setInstallationDate(df.format(filter.getInstallationDate()));
        pop.setPassedWaterVolume(filter.getPassedWaterVolume());
        pop.setFullName(fullName);
        if (filter.getName().equals(FilterName.A13)) pop.setLastRegeneration(df.format(filter.getLastRegeneration()));
        pop.setWearPercentage(getWearPercentage(filter));
        return pop;
    }

    private int getWearPercentage(Filter filter) {
        long today = new Date().getTime();
        switch (filter.getName()) {
            case IN: return (int) ((float) (filter.getPassedWaterVolume())/DataLimit.IN * 100);
            case PF: return (int) ((float) (filter.getPassedWaterVolume())/DataLimit.PF * 100);
            case DK: return (int) ((float) ((today - filter.getInstallationDate().getTime()))/DataLimit.DK * 100);
            case A13: return (int) ((float) ((today - filter.getLastRegeneration().getTime()))/DataLimit.A13 * 100);
            case KF: return (int) ((float) (filter.getPassedWaterVolume())/DataLimit.KF * 100);
            case AF: return (int) ((float) (filter.getPassedWaterVolume())/DataLimit.AF * 100);
            case FSD: return (int) ((float) (filter.getPassedWaterVolume())/DataLimit.FSD * 100);
            default: return 0;
        }
    }
}
