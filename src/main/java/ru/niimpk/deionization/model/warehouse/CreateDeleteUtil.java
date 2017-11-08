package ru.niimpk.deionization.model.warehouse;

import org.springframework.format.annotation.DateTimeFormat;
import ru.niimpk.deionization.model.filters.FilterName;

import java.util.Date;

public class CreateDeleteUtil {

    private FilterName name;
    private int amount;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date;

    public FilterName getName() {
        return name;
    }

    public void setName(FilterName name) {
        this.name = name;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
