package com.example.model;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.util.*;

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
    @Column(unique = true,nullable = false)
    private Long phoneNumber;
    @Column(unique = true,nullable = false)
    //@Email(message = "*Please provide a valid Email")
    //@NotEmpty(message = "*Please provide an email")
    private String email;

    private Double lastSearchedDailyLevel;
    private Double lastSearchedWeeklyLevel;
    private Date subscriptionEndDate;

    public Date getSubscriptionEndDate() {
        return subscriptionEndDate;
    }

    public void setSubscriptionEndDate(final Date subscriptionEndDate) {
        this.subscriptionEndDate = new Date(subscriptionEndDate.getTime());
    }

    @OneToMany(mappedBy = "userDetails",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<UserSubscriptionDetails> userSubscriptionDetailses=new ArrayList<>();

    public UserDetails() {
        this.subscriptionEndDate=getFreeTrailSubscription();
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
        this.subscriptionEndDate=getFreeTrailSubscription();
    }

    private Date getFreeTrailSubscription() {
        final Calendar instance = Calendar.getInstance();
        instance.add(Calendar.DATE,7);
        instance.set(Calendar.HOUR,0);
        instance.set(Calendar.MINUTE,0);
        instance.set(Calendar.SECOND,0);
        return instance.getTime();
    }

    public UserDetails(final String userName, final String password, final Boolean isAdmin, final String lang,
        final Long phoneNumber, final String eMail) {
        this.userName = userName;
        this.password = password;
        this.isAdmin = isAdmin;
        this.phoneNumber = phoneNumber;
        this.email = eMail;
        this.lang = lang;
        this.subscriptionEndDate=getFreeTrailSubscription();
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

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public Long getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(final Long phoneNumber) {
        this.phoneNumber = phoneNumber;
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

    public List<UserSubscriptionDetails> getUserSubscriptionDetailses() {
        return userSubscriptionDetailses;
    }

    public void setUserSubscriptionDetailses(List<UserSubscriptionDetails> userSubscriptionDetailses) {
        this.userSubscriptionDetailses = userSubscriptionDetailses;
    }
}
