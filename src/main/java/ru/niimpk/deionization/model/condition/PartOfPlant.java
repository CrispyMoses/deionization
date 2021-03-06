package ru.niimpk.deionization.model.condition;


public class PartOfPlant {
    private String installationDate;
    private int passedWaterVolume;
    private String fullName;
    private int wearPercentage;
    private String lastRegeneration;
    private String plantMappingName;
    private String filterName;


    public PartOfPlant() {
    }

    public PartOfPlant(String fullName, int wearPercentage, String lastRegeneration) {
        this.fullName = fullName;
        this.wearPercentage = wearPercentage;
        this.lastRegeneration = lastRegeneration;
    }

    public String getFilterName() {
        return filterName;
    }

    public void setFilterName(String filterName) {
        this.filterName = filterName;
    }

    public String getPlantMappingName() {
        return plantMappingName;
    }

    public void setPlantMappingName(String plantMappingName) {
        this.plantMappingName = plantMappingName;
    }

    public String getInstallationDate() {
        return installationDate;
    }

    public void setInstallationDate(String installationDate) {
        this.installationDate = installationDate;
    }

    public int getPassedWaterVolume() {
        return passedWaterVolume;
    }

    public void setPassedWaterVolume(int passedWaterVolume) {
        this.passedWaterVolume = passedWaterVolume;
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

    public String getLastRegeneration() {
        return lastRegeneration;
    }

    public void setLastRegeneration(String lastRegeneration) {
        this.lastRegeneration = lastRegeneration;
    }
}
