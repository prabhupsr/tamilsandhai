package com.example.pojo;

/**
 * @author mchidambaranathan 4/20/2017
 */
public class Levels {
    private String name;
    private Double level;
    private String mcl;
    private String tamilName;

    public String getTamilName() {
        return tamilName;
    }

    public void setTamilName(final String tamilName) {
        this.tamilName = tamilName;
    }

    public Levels(final Double level,final String name, final String mcl) {
        this.name = name;
        this.level = level;
        this.mcl = mcl;
    }

    public Levels() {
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
