package ru.niimpk.deionization.model.condition;

import ru.niimpk.deionization.model.filters.Filter;

import javax.persistence.*;

@Entity
@Table(name = "plant_mapping")
public class PlantMapping {

    @Id
    @Column(name = "name")
    @Enumerated(EnumType.STRING)
    private PlantMappingName name;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "filter_id")
    private Filter filter;

    public PlantMappingName getName() {
        return name;
    }

    public void setName(PlantMappingName name) {
        this.name = name;
    }

    public Filter getFilter() {
        return filter;
    }

    public void setFilter(Filter filter) {
        this.filter = filter;
    }
}
