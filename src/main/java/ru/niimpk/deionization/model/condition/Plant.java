package ru.niimpk.deionization.model.condition;

public class Plant {
    private PartOfPlant IN1;
    private PartOfPlant IN2;
    private PartOfPlant PF;
    private PartOfPlant DK;
    private PartOfPlant A13;
    private PartOfPlant KF;
    private PartOfPlant AF;
    private PartOfPlant FSD;
    private Reservoir E;
    private PartOfPlant FSDr;

    public PartOfPlant getIN1() {
        return IN1;
    }

    public void setIN1(PartOfPlant IN1) {
        this.IN1 = IN1;
    }

    public PartOfPlant getIN2() {
        return IN2;
    }

    public void setIN2(PartOfPlant IN2) {
        this.IN2 = IN2;
    }

    public PartOfPlant getPF() {
        return PF;
    }

    public void setPF(PartOfPlant PF) {
        this.PF = PF;
    }

    public PartOfPlant getDK() {
        return DK;
    }

    public void setDK(PartOfPlant DK) {
        this.DK = DK;
    }

    public PartOfPlant getA13() {
        return A13;
    }

    public void setA13(PartOfPlant a13) {
        A13 = a13;
    }

    public PartOfPlant getKF() {
        return KF;
    }

    public void setKF(PartOfPlant KF) {
        this.KF = KF;
    }

    public PartOfPlant getAF() {
        return AF;
    }

    public void setAF(PartOfPlant AF) {
        this.AF = AF;
    }

    public PartOfPlant getFSD() {
        return FSD;
    }

    public void setFSD(PartOfPlant FSD) {
        this.FSD = FSD;
    }

    public Reservoir getE() {
        return E;
    }

    public void setE(Reservoir e) {
        E = e;
    }

    public PartOfPlant getFSDr() {
        return FSDr;
    }

    public void setFSDr(PartOfPlant FSDr) {
        this.FSDr = FSDr;
    }
}
