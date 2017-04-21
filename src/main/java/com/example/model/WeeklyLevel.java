package com.example.model;

import com.example.pojo.Levels;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * @author mchidambaranathan 4/19/2017
 */
@Entity
public class WeeklyLevel extends Levels {
    @Id
    @GeneratedValue
    private int id;
    private String name;
    private String tamilName;
    private Double level;
    private String mcl;

    public String getTamilName() {
        return tamilName;
    }

    public void setTamilName(final String tamilName) {
        this.tamilName = tamilName;
    }

    public WeeklyLevel(final Double level,final String name, final String tamilName,  final String mcl) {
        this.name = name;
        this.tamilName = tamilName;
        this.level = level;
        this.mcl = mcl;
    }

    public WeeklyLevel() {
    }

       public int getId() {
        return id;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public Double getLevel() {
        return level;
    }

    public void setLevel(final Double level) {
        this.level = level;
    }

    public String getMcl() {
        return mcl;
    }

    public void setMcl(final String mcl) {
        this.mcl = mcl;
    }
}
