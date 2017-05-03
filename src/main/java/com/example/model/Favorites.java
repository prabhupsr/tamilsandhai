package com.example.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * @author mchidambaranathan 4/19/2017
 */
@Entity
public class Favorites {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String symbol;
    private Integer pos;
    private Double dailyClose;
    private Double weeklyClose;
    private String oldName;
    private String userId;

    public Favorites() {
    }
    public Favorites(final Double dailyClose, final Double weeklyClose, final String name,  final String symbol,final String userId) {
        this.dailyClose = dailyClose;
        this.weeklyClose = weeklyClose;
        this.name = name;
        this.userId = userId;
        this.symbol=symbol;
    }

    public Integer getPos() {
        return pos;
    }

    public void setPos(final Integer pos) {
        this.pos = pos;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(final String symbol) {
        this.symbol = symbol;
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

    public String getOldName() {
        return oldName;
    }

    public void setOldName(final String oldName) {
        this.oldName = oldName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(final String userId) {
        this.userId = userId;
    }
}
