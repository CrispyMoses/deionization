package ru.niimpk.deionization.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "water_counters")
public class StatementCounter {

    @Column(name = "date")
    private Date date;

    @Column(name = "in_statement")
    private int incomeStatement;

    @Column(name = "out_satement")
    private int outStatement;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getIncomeStatement() {
        return incomeStatement;
    }

    public void setIncomeStatement(int incomeStatement) {
        this.incomeStatement = incomeStatement;
    }

    public int getOutStatement() {
        return outStatement;
    }

    public void setOutStatement(int outStatement) {
        this.outStatement = outStatement;
    }
}
