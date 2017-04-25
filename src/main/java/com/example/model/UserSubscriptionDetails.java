package com.example.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.Date;

/**
 * @author mchidambaranathan 4/24/2017
 */
@Entity
public class UserSubscriptionDetails {

    @Id
    @GeneratedValue
    private Long id;
    private Date startDate;
    private Date endDate;

    @ManyToOne
    private UserDetails userDetails;

    public UserSubscriptionDetails() {
    }

    public UserSubscriptionDetails(final Date startDate, final Date endDate, final UserDetails userDetails) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.userDetails = userDetails;
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(final Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(final Date endDate) {
        this.endDate = endDate;
    }

    public UserDetails getUserDetails() {
        return userDetails;
    }

    public void setUserDetails(final UserDetails userDetails) {
        this.userDetails = userDetails;
    }
}
