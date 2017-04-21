package com.example.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.util.Date;

/**
 * @author mchidambaranathan 4/20/2017
 */
@Entity
public class UserLoginActivityLog {
    @Id
    @GeneratedValue
    private Long id;
    private Date date;
    private Integer activityType;
    private Long userId;
    private String userName;
    private Date lastActivityTime;
    @Transient
    private String errorString;

    public UserLoginActivityLog(final String errorString) {
        this.errorString = errorString;
    }

    public String getErrorString() {
        return errorString;
    }

    public void setErrorString(final String errorString) {
        this.errorString = errorString;
    }

    private String loginTime;
    private String lastActivityTimeString;

    @SuppressWarnings("CallToDateToString")
    public void setStringVal(){
        this.loginTime=this.getDate().toString();
        this.lastActivityTimeString=this.getLastActivityTime().toString();
    }

    public String getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(final String loginTime) {
        this.loginTime = loginTime;
    }

    public String getLastActivityTimeString() {
        return lastActivityTimeString;
    }

    public void setLastActivityTimeString(final String lastActivityTimeString) {
        this.lastActivityTimeString = lastActivityTimeString;
    }

    public Date getLastActivityTime() {
        return lastActivityTime;
    }

    public void setLastActivityTime(final Date lastActivityTime) {
        this.lastActivityTime = new Date(lastActivityTime.getTime());
    }

    public UserLoginActivityLog() {
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(final String userName) {
        this.userName = userName;
    }

    public UserLoginActivityLog(final Long userId,final  String userName, final Integer activityType) {
        this.date = new Date();
        this.activityType = activityType;
        this.userId = userId;
        this.userName=userName;
        this.lastActivityTime=new Date();
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(final Date date) {
        this.date = new Date(date.getTime());
    }

    public Integer getActivityType() {
        return activityType;
    }

    public void setActivityType(final Integer activityType) {
        this.activityType = activityType;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(final Long userId) {
        this.userId = userId;
    }

}
