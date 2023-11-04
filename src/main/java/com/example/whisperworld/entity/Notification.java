package com.example.whisperworld.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "notifications")
public class Notification implements Serializable {
    @Id
    @Column(name = "NotificationID")
    private int notificationID;

    @Column(name = "NotificationContent")
    private String notificationContent;

    @Column(name = "NotificationTime")
    private Date notificationTime;

    @ManyToOne
    @JoinColumn(name = "SupervisorID")
    private Supervisor supervisor;

    public int getNotificationID() {
        return notificationID;
    }

    public void setNotificationID(int notificationID) {
        this.notificationID = notificationID;
    }

    public String getNotificationContent() {
        return notificationContent;
    }

    public void setNotificationContent(String notificationContent) {
        this.notificationContent = notificationContent;
    }

    public Date getNotificationTime() {
        return notificationTime;
    }

    public void setNotificationTime(Date notificationTime) {
        this.notificationTime = notificationTime;
    }

    public Supervisor getSupervisor() {
        return supervisor;
    }

    public void setSupervisor(Supervisor supervisor) {
        this.supervisor = supervisor;
    }
}
