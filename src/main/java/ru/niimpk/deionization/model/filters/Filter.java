package ru.niimpk.deionization.model.filters;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "filters")
public class Filter {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "water_passed")
    private Integer passedWaterVolume;

    @Column(name = "name")
    @Enumerated(EnumType.STRING)
    private FilterName name;

    @Column(name = "purchase_date")
    private Date purchaseDate;

    @Column(name = "installation_date")
    private Date installationDate;

    @Column(name = "utilize_date")
    private Date utilizeDate;

    @Column(name = "location")
    @Enumerated(EnumType.STRING)
    private FilterLocation location;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public FilterLocation getLocation() {
        return location;
    }

    public void setLocation(FilterLocation location) {
        this.location = location;
    }

    public int getPassedWaterVolume() {
        return passedWaterVolume;
    }

    public void setPassedWaterVolume(int passedWaterVolume) {
        this.passedWaterVolume = passedWaterVolume;
    }

    public void setPassedWaterVolume(Integer passedWaterVolume) {
        this.passedWaterVolume = passedWaterVolume;
    }

    public FilterName getName() {
        return name;
    }

    public void setName(FilterName name) {
        this.name = name;
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

    public Date getUtilizeDate() {
        return utilizeDate;
    }

    public void setUtilizeDate(Date utilizeDate) {
        this.utilizeDate = utilizeDate;
    }
}
