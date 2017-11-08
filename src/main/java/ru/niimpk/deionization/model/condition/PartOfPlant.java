package ru.niimpk.deionization.model.condition;

import org.springframework.format.annotation.DateTimeFormat;
import ru.niimpk.deionization.model.filters.Filter;

import java.util.Date;

public class PartOfPlant {
    private Filter filter;
    private String fullName;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date lastRegeneration;
    private int wearPercentage;

    public PartOfPlant() {
    }

    public PartOfPlant(String fullName, int wearPercentage, Date lastRegeneration) {
        this.fullName = fullName;
        this.wearPercentage = wearPercentage;
        this.lastRegeneration = lastRegeneration;
    }

    public Date getLastRegeneration() {
        return lastRegeneration;
    }

    public void setLastRegeneration(Date lastRegeneration) {
        this.lastRegeneration = lastRegeneration;
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
