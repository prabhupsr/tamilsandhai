package com.example.pojo;

/**
 * @author mchidambaranathan 4/23/2017
 */
public class SunsetSunRaiseDetails
{
    private String day;
    private String startTime;
    private String endTime;

    public SunsetSunRaiseDetails() {
    }

    public SunsetSunRaiseDetails(final String day, final String startTime, final String endTime) {
        this.day = day;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public String getDay() {
        return day;
    }

    public void setDay(final String day) {
        this.day = day;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(final String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(final String endTime) {
        this.endTime = endTime;
    }
}
