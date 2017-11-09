package ru.niimpk.deionization.DAO;


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
import java.util.List;

@Repository
public class MainDAO {

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

    public void persistStatement(StatementCounter sc) {em.persist(sc);}

    public Reservoir getReservoir() {
        return em.find(Reservoir.class, new Integer(1));
    }

    public void persistReservoir(Reservoir reservoir) {em.persist(reservoir);}
}
