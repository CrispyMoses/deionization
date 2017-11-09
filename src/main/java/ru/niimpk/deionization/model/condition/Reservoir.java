package ru.niimpk.deionization.model.condition;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "reservoir")
public class Reservoir {

    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "last_regeneration")
    private Date lastRegeneration;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getLastRegeneration() {
        return lastRegeneration;
    }

    public void setLastRegeneration(Date lastRegeneration) {
        this.lastRegeneration = lastRegeneration;
    }
}