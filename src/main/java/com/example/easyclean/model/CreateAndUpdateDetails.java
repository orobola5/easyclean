package com.example.easyclean.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Embeddable
public class CreateAndUpdateDetails implements Serializable {

    @Temporal(TemporalType.DATE)
    @Column(name = "created_time", nullable = false)
    private Date createdTime;

    @JsonIgnoreProperties({"lastName", "email", "phone", "role", "isEnabled", "createAndUpdateDetails"})
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "created_by", referencedColumnName = "id")
    private Users createdBy;

    @Temporal(TemporalType.DATE)
    @Column(name = "updated_time", nullable = true)
    private Date updatedTime;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "updated_by", referencedColumnName = "id")
    private Users updatedby;

    public CreateAndUpdateDetails() {
    }

    public CreateAndUpdateDetails(Date createdTime, Users createdBy) {
        this.createdTime = createdTime;
        this.createdBy = createdBy;
    }

    public CreateAndUpdateDetails(Date createdTime, Users createdBy, Date updatedTime, Users updatedby) {
        this.createdTime = createdTime;
        this.createdBy = createdBy;
        this.updatedTime = updatedTime;
        this.updatedby = updatedby;
    }

    public CreateAndUpdateDetails(Date currentDate) {
    }


    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Users getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Users createdBy) {
        this.createdBy = createdBy;
    }

    public Date getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
    }

    public Users getUpdatedby() {
        return updatedby;
    }

    public void setUpdatedby(Users updatedby) {
        this.updatedby = updatedby;
    }

    @Override
    public String toString() {
        return "CreateAndUpdateDetails{" + "createdTime=" + createdTime + ", createdBy=" + createdBy + ", updatedTime=" + updatedTime + ", " +
                "updatedby=" + updatedby + '}';
    }
}
