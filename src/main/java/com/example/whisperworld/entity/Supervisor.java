package com.example.whisperworld.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "supervisors")
public class Supervisor implements Serializable {
    @Id
    @Column(name = "SupervisorID")
    private int supervisorID;

    @Column(name = "GrantTime")
    private Date grantTime;

    public int getSupervisorID() {
        return supervisorID;
    }

    public void setSupervisorID(int supervisorID) {
        this.supervisorID = supervisorID;
    }

    public Date getGrantTime() {
        return grantTime;
    }

    public void setGrantTime(Date grantTime) {
        this.grantTime = grantTime;
    }
}
