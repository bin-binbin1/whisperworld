package com.example.whisperworld.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "crowds")
public class Crowds {

    @Id
    @Column(name = "GroupID")
    private int groupId;

    @Column(name = "GroupName")
    private String groupName;

    @Column(name = "CreateDate")
    @Temporal(TemporalType.DATE)
    private Date createDate;

    @Column(name = "GroupNum")
    private int groupNum;

    @Column(name = "GroupBackground")
    private String groupBackground;

    @ManyToOne
    @JoinColumn(name = "MasterID", referencedColumnName = "UserID")
    private User master;

    @ManyToOne
    @JoinColumn(name = "ManagerID", referencedColumnName = "UserID")
    private User manager;

    public Crowds() {
    }

    public Crowds(int groupId, String groupName, Date createDate, int groupNum, String groupBackground, User master, User manager) {
        this.groupId = groupId;
        this.groupName = groupName;
        this.createDate = createDate;
        this.groupNum = groupNum;
        this.groupBackground = groupBackground;
        this.master = master;
        this.manager = manager;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public int getGroupNum() {
        return groupNum;
    }

    public void setGroupNum(int groupNum) {
        this.groupNum = groupNum;
    }

    public String getGroupBackground() {
        return groupBackground;
    }

    public void setGroupBackground(String groupBackground) {
        this.groupBackground = groupBackground;
    }

    public User getMaster() {
        return master;
    }

    public void setMaster(User master) {
        this.master = master;
    }

    public User getManager() {
        return manager;
    }

    public void setManager(User manager) {
        this.manager = manager;
    }
}
