package com.example.pojo;

import java.util.Date;

/**
 * @author mchidambaranathan 4/20/2017
 */
public class BasicUserDetails {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    private String userName;
    private Double lastSearchedDailyLevel;
    private Double lastSearchedWeeklyLevel;
    private Date subscriptionEndDate;
    private Long phoneNumber;
    private String email;

    @SuppressWarnings("AssignmentToDateFieldFromParameter")
    public BasicUserDetails(
        final String name,
        final String userName,
        final Double lastSearchedDailyLevel,
        final Double lastSearchedWeeklyLevel,
        final Date subscriptionEndDate) {
        this.name = name;
        this.userName = userName;
        this.subscriptionEndDate = subscriptionEndDate;
        this.lastSearchedDailyLevel = lastSearchedDailyLevel;
        this.lastSearchedWeeklyLevel = lastSearchedWeeklyLevel;
    }
    public BasicUserDetails(
        final String name,
        final String userName,
        final Date subscriptionEndDate,
        final Long phoneNumber,
        final String email) {
        this.name = name;
        this.userName = userName;
        this.subscriptionEndDate = new Date(subscriptionEndDate.getTime());
    }

    public Date getSubscriptionEndDate() {
        return subscriptionEndDate;
    }

    @SuppressWarnings("AssignmentToDateFieldFromParameter")
    public void setSubscriptionEndDate(final Date subscriptionEndDate) {
        this.subscriptionEndDate = subscriptionEndDate;
    }

    public Long getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(final Long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

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
