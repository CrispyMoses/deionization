package ru.niimpk.deionization.model.condition;

import ru.niimpk.deionization.model.filters.Filter;

import javax.persistence.*;

@Entity
@Table(name = "filter_mapping")
public class PlantMapping {

    @Id
    @Column(name = "name")
    private String name;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "filter_id")
    private Filter filter;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Filter getFilter() {
        return filter;
    }

    public void setFilter(Filter filter) {
        this.filter = filter;
    }
}
