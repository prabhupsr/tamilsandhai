package com.example.pojo;

import java.util.ArrayList;
import java.util.List;

/**
 * @author mchidambaranathan 4/23/2017
 */
public class SunRaiseDetails {

    private String sunRaise;
    private String sunSet;
    private String todaysDate;
    private String todaysDay;

    public String getTodaysDate() {
        return todaysDate;
    }

    public void setTodaysDate(final String todaysDate) {
        this.todaysDate = todaysDate;
    }

    public String getTodaysDay() {
        return todaysDay;
    }

    public void setTodaysDay(final String todaysDay) {
        this.todaysDay = todaysDay;
    }

    private final List<SunsetSunRaiseDetails> sunsetSunRaiseDetailses=new ArrayList<>();


    public String getSunRaise() {
        return sunRaise;
    }

    public void setSunRaise(final String sunRaise) {
        this.sunRaise = sunRaise;
    }

    public String getSunSet() {
        return sunSet;
    }

    public void setSunSet(final String sunSet) {
        this.sunSet = sunSet;
    }

    public List<SunsetSunRaiseDetails> getSunsetSunRaiseDetailses() {
        return sunsetSunRaiseDetailses;
    }
}

