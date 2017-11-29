package ru.niimpk.deionization.model.counters;

import org.springframework.format.annotation.DateTimeFormat;

import java.text.SimpleDateFormat;
import java.util.Date;

public class StatementDateLimit {

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date before;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date after;

    public Date getBefore() {
        return before;
    }

    public void setBefore(Date before) {
        this.before = before;
    }

    public Date getAfter() {
        return after;
    }

    public void setAfter(Date uAfter) {
        this.after = uAfter;
    }

    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        return "Дата до - " + sdf.format(before) + " дата до - " + sdf.format(after);
    }
}
