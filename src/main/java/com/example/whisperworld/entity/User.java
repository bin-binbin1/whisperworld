package com.example.whisperworld.entity;
import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "users")
public class User {
    @Id
    @Column(name = "UserID")
    private int userId;

    @Column(name = "UserName", length = 20, nullable = false)
    private String userName;

    @Column(name = "UserPassword", length = 15, nullable = false)
    private String userPassword;

    @Column(name = "UserSex", length = 1, nullable = false)
    private String userSex;

    @Column(name = "URDate", nullable = false)
    private Date urDate;

    @Column(name = "UserPhone", length = 11, nullable = false)
    private String userPhone;

    @Column(name = "UserState", length = 10, nullable = false)
    private String userState;

    @Column(name = "UserLevel", nullable = false)
    private int userLevel;

    @Column(name = "UserBDate", nullable = false)
    private Date userBDate;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserSex() {
        return userSex;
    }

    public void setUserSex(String userSex) {
        this.userSex = userSex;
    }

    public Date getUrDate() {
        return urDate;
    }

    public void setUrDate(Date urDate) {
        this.urDate = urDate;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserState() {
        return userState;
    }

    public void setUserState(String userState) {
        this.userState = userState;
    }

    public int getUserLevel() {
        return userLevel;
    }

    public void setUserLevel(int userLevel) {
        this.userLevel = userLevel;
    }

    public Date getUserBDate() {
        return userBDate;
    }

    public void setUserBDate(Date userBDate) {
        this.userBDate = userBDate;
    }

    // 省略 getter 和 setter 方法
}
