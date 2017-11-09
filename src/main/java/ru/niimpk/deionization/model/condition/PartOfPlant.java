package ru.niimpk.deionization.model.condition;


import ru.niimpk.deionization.model.filters.Filter;

import java.util.Date;

public class PartOfPlant {
    private Filter filter;
    private Date installationDate;
    private int passedWaterVolume;
    private String fullName;
    private int wearPercentage;
    private Date lastRegeneration;


    public PartOfPlant() {
    }

    public PartOfPlant(String fullName, int wearPercentage, Date lastRegeneration) {
        this.fullName = fullName;
        this.wearPercentage = wearPercentage;
        this.lastRegeneration = lastRegeneration;
    }

    public Date getInstallationDate() {
        return installationDate;
    }

    public void setInstallationDate(Date installationDate) {
        this.installationDate = installationDate;
    }

    public int getPassedWaterVolume() {
        return passedWaterVolume;
    }

    public void setPassedWaterVolume(int passedWaterVolume) {
        this.passedWaterVolume = passedWaterVolume;
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
