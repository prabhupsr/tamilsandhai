package com.example.pojo;

/**
 * @author mchidambaranathan 4/20/2017
 */
public class BasicUserDetails {
    private String userName;
    private Double lastSearchedDailyLevel;
    private Double lastSearchedWeeklyLevel;

    public BasicUserDetails() {
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(final String userName) {
        this.userName = userName;
    }

    public Double getLastSearchedDailyLevel() {
        return lastSearchedDailyLevel;
    }

    public void setLastSearchedDailyLevel(final Double lastSearchedDailyLevel) {
        this.lastSearchedDailyLevel = lastSearchedDailyLevel;
    }

    public Double getLastSearchedWeeklyLevel() {
        return lastSearchedWeeklyLevel;
    }

    public void setLastSearchedWeeklyLevel(final Double lastSearchedWeeklyLevel) {
        this.lastSearchedWeeklyLevel = lastSearchedWeeklyLevel;
    }

    public BasicUserDetails(
        final String userName,
        final Double lastSearchedDailyLevel,
        final Double lastSearchedWeeklyLevel) {
        this.userName = userName;
        this.lastSearchedDailyLevel = lastSearchedDailyLevel;
        this.lastSearchedWeeklyLevel = lastSearchedWeeklyLevel;
    }
}
