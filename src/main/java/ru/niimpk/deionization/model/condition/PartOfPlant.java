package ru.niimpk.deionization.model.condition;

import ru.niimpk.deionization.model.filters.Filter;

public class PartOfPlant {
    private Filter filter;
    private String fullName;
    private int wearPercentage;

    public PartOfPlant() {
    }

    public PartOfPlant(String fullName, int wearPercentage) {
        this.fullName = fullName;
        this.wearPercentage = wearPercentage;
    }

    public Filter getFilter() {
        return filter;
    }

    public void setFilter(Filter filter) {
        this.filter = filter;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getWearPercentage() {
        return wearPercentage;
    }

    public void setWearPercentage(int wearPercentage) {
        this.wearPercentage = wearPercentage;
    }
}
