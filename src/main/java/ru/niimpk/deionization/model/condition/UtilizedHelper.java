package ru.niimpk.deionization.model.condition;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class UtilizedHelper {

    private String filterName;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date uBefore;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date uAfter;

    public String getFilterName() {
        return filterName;
    }

    public void setFilterName(String filterName) {
        this.filterName = filterName;
    }

    public Date getuBefore() {
        return uBefore;
    }

    public void setuBefore(Date uBefore) {
        this.uBefore = uBefore;
    }

    public Date getuAfter() {
        return uAfter;
    }

    public void setuAfter(Date uAfter) {
        this.uAfter = uAfter;
    }

    @Override
    public String toString() {
        return "UtilizedHelper{" +
                "filterName='" + filterName + '\'' +
                ", uBefore=" + uBefore +
                ", uAfter=" + uAfter +
                '}';
    }
}
