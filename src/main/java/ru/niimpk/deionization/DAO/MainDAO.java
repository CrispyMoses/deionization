package ru.niimpk.deionization.DAO;


import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.niimpk.deionization.model.condition.PlantMapping;
import ru.niimpk.deionization.model.condition.PlantMappingName;
import ru.niimpk.deionization.model.condition.Reservoir;
import ru.niimpk.deionization.model.counters.StatementCounter;
import ru.niimpk.deionization.model.filters.Filter;
import ru.niimpk.deionization.model.filters.FilterLocation;
import ru.niimpk.deionization.model.filters.FilterName;

import javax.persistence.EntityManager;
import java.util.Date;
import java.util.List;

@Repository
public class MainDAO {

    Logger log = Logger.getLogger(MainDAO.class);

    @Autowired
    private EntityManager em;

    public int getWarehouseFiltersAmount(FilterName name) {
        List<Filter> list = em
                .createQuery("select f from Filter f where f.location = :location AND f.name = :name", Filter.class)
                .setParameter("location", FilterLocation.WAREHOUSE)
                .setParameter("name", name)
                .getResultList();
        return list != null ? list.size() : 0;
    }

    public void persistFilter(Filter filter) {
        em.persist(filter);
    }

    public Filter getOlderFilter(FilterName name, FilterLocation location) {
       return em.createQuery("select f from Filter f where f.location = :location and f.name = :name order by f.purchaseDate", Filter.class)
                .setParameter("location", location)
                .setParameter("name", name)
                .getResultList().get(0);
    }

    public void deleteFilter(Filter filter) {
        em.createQuery("delete from Filter f where f.id = :id")
                .setParameter("id", filter.getId())
                .executeUpdate();
    }

    public Filter getWorkFilter(PlantMappingName name) {
        return em.createQuery("select pm from PlantMapping pm where pm.name = :name", PlantMapping.class)
                .setParameter("name", name)
                .getResultList().get(0).getFilter();
    }

    public void updateFilterMapping(Filter filter, PlantMappingName name) {
        em.createQuery("update PlantMapping pm set pm.filter = :filter where pm.name = :name")
                .setParameter("filter", filter)
                .setParameter("name", name)
                .executeUpdate();
    }

    public void persistStatement(StatementCounter sc) {em.persist(sc);}

    public StatementCounter getLastStatement() {
        return em.createQuery("select sc from StatementCounter sc order by sc.date desc", StatementCounter.class)
                .getResultList().get(0);
    }

    public Reservoir getReservoir() {
        return em.find(Reservoir.class, new Integer(1));
    }

    public void persistReservoir(Reservoir reservoir) {em.persist(reservoir);}


    public void updateRegenerateDate(PlantMappingName name, Date date) {
        if (name.equals(PlantMappingName.A13))
            em.createQuery("update Filter f set f.lastRegeneration = :date where f.location = :location and f.name = :name")
                    .setParameter("date", date)
                    .setParameter("location", FilterLocation.WORK)
                    .setParameter("name", FilterName.A13)
                    .executeUpdate();
        else if (name.equals(PlantMappingName.E))
            em.createQuery("update Reservoir r set r.lastRegeneration = :date where r.id = :id")
                    .setParameter("id", new Integer(1))
                    .setParameter("date", date)
                    .executeUpdate();
    }

    public void utilizeFilter(Filter filter, Date date) {
        em.createQuery("update Filter f set f.location =:location, f.utilizeDate = :date where f.id = :id")
                .setParameter("location", FilterLocation.UTILIZED)
                .setParameter("date", date)
                .setParameter("id", filter.getId())
                .executeUpdate();
    }

    public void goToWork(Filter filter, Date date) {
        em.createQuery("update Filter f set f.location = :location, f.installationDate = :date, f.passedWaterVolume = :passed where f.id = :id")
                .setParameter("location", FilterLocation.WORK)
                .setParameter("date", date)
                .setParameter("passed", 0)
                .setParameter("id", filter.getId())
                .executeUpdate();
    }

    public List<StatementCounter> getStatement(Date before, Date after) {
        return em.createQuery("select sc from StatementCounter sc where sc.date <= :before and sc.date >= :after order by sc.date desc", StatementCounter.class)
                .setParameter("before", before)
                .setParameter("after", after).getResultList();
    }
}
