package com.example.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;
import java.util.Objects;

/**
 * @author mchidambaranathan 4/17/2017
 */

@Entity
public class UserDetails {
    @Id
    @GeneratedValue
    private Long userId;
    private String userName;
    private String password;
    private String age;
    private Date dataBirth;
    private Double lastSearchedDailyLevel;
    private Double lastSearchedWeeklyLevel;

    public UserDetails() {
    }

    private Boolean isActive=Boolean.TRUE;
    private Boolean isAdmin=Boolean.FALSE;

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(final Boolean active) {
        isActive = active;
    }

    public Boolean getAdmin() {
        return isAdmin;
    }

    public void setAdmin(final Boolean admin) {
        isAdmin = admin;
    }

    public UserDetails(final String userName, final String password, final Boolean isAdmin, final String lang) {
        this.userName = userName;
        this.password = password;
        this.isAdmin = isAdmin;
        this.lang = lang;
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

    private String lang;

    public String getLang() {
        return lang;
    }

    public void setLang(final String lang) {
        this.lang = lang;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(final Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(final String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public String getAge() {
        return age;
    }

    public void setAge(final String age) {
        this.age = age;
    }

    public Date getDataBirth() {
        return dataBirth;
    }

    public void setDataBirth(final Date dataBirth) {
        this.dataBirth = dataBirth;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserDetails)) {
            return false;
        }
        final UserDetails that = (UserDetails) o;
        return Objects.equals(userId, that.userId) &&
            Objects.equals(userName, that.userName) &&
            Objects.equals(password, that.password) &&
            Objects.equals(age, that.age) &&
            Objects.equals(dataBirth, that.dataBirth) &&
            Objects.equals(lastSearchedDailyLevel, that.lastSearchedDailyLevel) &&
            Objects.equals(lastSearchedWeeklyLevel, that.lastSearchedWeeklyLevel);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            userId,
            userName,
            password,
            age,
            dataBirth,
            lastSearchedDailyLevel,
            lastSearchedWeeklyLevel);
    }
}
