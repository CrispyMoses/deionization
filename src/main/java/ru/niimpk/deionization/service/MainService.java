package ru.niimpk.deionization.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.niimpk.deionization.DAO.MainDAO;
import ru.niimpk.deionization.model.condition.*;
import ru.niimpk.deionization.model.counters.StatementCounter;
import ru.niimpk.deionization.model.counters.StatementDateLimit;
import ru.niimpk.deionization.model.counters.WaterDischarge;
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


    public List<PartOfPlant> getPatrsOfPlant() {
        long today = new Date().getTime();
        long msPerDay = 1000 * 60 *60 * 24;
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
        PartOfPlant pop = new PartOfPlant(FilterFullName.E,
                (int) ((float) ((today - reservoir.getLastRegeneration().getTime()))/(msPerDay * DataLimit.E) * 100),
                df.format(reservoir.getLastRegeneration()));
        pop.setLastRegeneration(df.format(reservoir.getLastRegeneration()));
        pop.setPlantMappingName(PlantMappingName.E.toString());
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
        pop.setPlantMappingName(name.toString());
        pop.setFilterName(filter.getName().toString());
        if (filter.getName().equals(FilterName.A13)) pop.setLastRegeneration(df.format(filter.getLastRegeneration()));
        pop.setWearPercentage(getWearPercentage(filter, name));
        return pop;
    }

    private int getWearPercentage(Filter filter, PlantMappingName name) {
        long today = new Date().getTime();
        long msPerDay = 1000 * 60 *60 * 24;
        switch (filter.getName()) {
            case IN: return (int) ((float) (filter.getPassedWaterVolume())/DataLimit.IN * 100);
            case PF: return (int) ((float) (filter.getPassedWaterVolume())/DataLimit.PF * 100);
            case DK: return (int) ((float) ((today - filter.getInstallationDate().getTime()))/(msPerDay * DataLimit.DK) * 100);
            case A13: return (int) ((float) ((today - filter.getLastRegeneration().getTime()))/(msPerDay * DataLimit.A13) * 100);
            case KF: return (int) ((float) (filter.getPassedWaterVolume())/DataLimit.KF * 100);
            case AF: return (int) ((float) (filter.getPassedWaterVolume())/DataLimit.AF * 100);
            case FSD: if (name == PlantMappingName.FSD) return (int) ((float) (filter.getPassedWaterVolume())/DataLimit.FSD * 100);
                      else return (int) ((float) (filter.getPassedWaterVolume())/DataLimit.FSDr * 100);
            default: return 0;
        }
    }


    public void replaceFilter(PlantMappingName name, FilterName filterName) throws IndexOutOfBoundsException {
        if (name.equals(PlantMappingName.E) || name.equals(PlantMappingName.A13)) {
            dao.updateRegenerateDate(name, new Date());
        } else {
            dao.utilizeFilter(dao.getWorkFilter(name), new Date());
            Filter filter = dao.getOlderFilter(filterName, FilterLocation.WAREHOUSE);
            dao.goToWork(filter, new Date());
            dao.updateFilterMapping(filter, name);
        }
    }


    public List<StatementCounter> getStatementList(StatementDateLimit sdl) {
        List<StatementCounter> list = dao.getStatement(sdl.getBefore(), sdl.getAfter());
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        logger.info(sdf.format(sdl.getAfter()) + sdf.format(sdl.getBefore()));
        return  list;
    }

    public WaterDischarge getDischarge(List<StatementCounter> list) {
        WaterDischarge wd = new WaterDischarge();
        long msPerDay = 1000 * 60 *60 * 24;
        int daysLeft = (int) Math.ceil((float) (list.get(0).getDate().getTime() - list.get(list.size() - 1).getDate().getTime())/msPerDay);
        int inWaterPassed = list.get(0).getIncomeStatement() - list.get(list.size() - 1).getIncomeStatement();
        int outWaterPassed = list.get(0).getOutStatement() - list.get(list.size() - 1).getOutStatement();
        int waterPerDay = (int) (outWaterPassed/(daysLeft - Math.floor((daysLeft  * 2)/7)));
        int waterPerMonth =  waterPerDay * 22;
        wd.setDischargePerDay(waterPerDay);
        wd.setDischargePerMonth(waterPerMonth);

        int KF = (int) Math.ceil((float)outWaterPassed / DataLimit.KF);
        wd.setKF(KF);
        wd.setAF(KF);
        wd.setFSD(KF + 1);
        wd.setIN((int) Math.ceil((float) inWaterPassed / DataLimit.IN) * 2);
        logger.info(wd.getIN());
        wd.setPF((int) Math.ceil((float) inWaterPassed / DataLimit.PF));
        return wd;
    }

    private void setWaterDischarge() {
    }
}
