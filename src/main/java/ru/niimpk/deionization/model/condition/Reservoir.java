package ru.niimpk.deionization.model.condition;

import java.util.Date;

public class Reservoir {
    private String name;
    private Date lastRegeneration;

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
