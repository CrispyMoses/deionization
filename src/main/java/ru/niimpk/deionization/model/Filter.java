package ru.niimpk.deionization.model;

import java.util.Date;

public class Filter {
    private int passedWaterVolume;
    private String name;
    private String fullName;
    private Date purchaseDate;
    private Date installationDate;
    private Date removeDate;

    public int getPassedWaterVolume() {
        return passedWaterVolume;
    }

    public void setPassedWaterVolume(int passedWaterVolume) {
        this.passedWaterVolume = passedWaterVolume;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Date getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(Date purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public Date getInstallationDate() {
        return installationDate;
    }

    public void setInstallationDate(Date installationDate) {
        this.installationDate = installationDate;
    }

    public Date getRemoveDate() {
        return removeDate;
    }

    public void setRemoveDate(Date removeDate) {
        this.removeDate = removeDate;
    }
}
