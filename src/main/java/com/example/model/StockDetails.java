package com.example.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * @author mchidambaranathan 4/21/2017
 */
@Entity
public class StockDetails {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String symbol;
    private Double dailyClose;
    private Double weeklyClose;
    private String type;

    public StockDetails() {
    }

    public StockDetails(final String name, final String symbol, final String type) {
        this.name = name;
        this.symbol = symbol;
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(final String symbol) {
        this.symbol = symbol;
    }

    public Double getDailyClose() {
        return dailyClose;
    }

    public void setDailyClose(final Double dailyClose) {
        this.dailyClose = dailyClose;
    }

    public Double getWeeklyClose() {
        return weeklyClose;
    }

    public void setWeeklyClose(final Double weeklyClose) {
        this.weeklyClose = weeklyClose;
    }

    public String getType() {
        return type;
    }

    public void setType(final String type) {
        this.type = type;
    }
}
