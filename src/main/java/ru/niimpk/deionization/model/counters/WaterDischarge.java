package ru.niimpk.deionization.model.counters;

import ru.niimpk.deionization.model.warehouse.Warehouse;

public class WaterDischarge {
    private int dischargePerMonth;
    private int dischargePerDay;

    private int IN;
    private int PF;
    private int KF;
    private int AF;
    private int FSD;

    private Warehouse wh;

    public int getDischargePerMonth() {
        return dischargePerMonth;
    }

    public void setDischargePerMonth(int dischargePerMonth) {
        this.dischargePerMonth = dischargePerMonth;
    }

    public int getDischargePerDay() {
        return dischargePerDay;
    }

    public void setDischargePerDay(int dischargePerDay) {
        this.dischargePerDay = dischargePerDay;
    }

    public int getIN() {
        return IN;
    }

    public void setIN(int IN) {
        this.IN = IN;
    }

    public int getPF() {
        return PF;
    }

    public void setPF(int PF) {
        this.PF = PF;
    }

    public int getKF() {
        return KF;
    }

    public void setKF(int KF) {
        this.KF = KF;
    }

    public int getAF() {
        return AF;
    }

    public void setAF(int AF) {
        this.AF = AF;
    }

    public int getFSD() {
        return FSD;
    }

    public void setFSD(int FSD) {
        this.FSD = FSD;
    }

    public Warehouse getWh() {
        return wh;
    }

    public void setWh(Warehouse wh) {
        this.wh = wh;
    }
}
