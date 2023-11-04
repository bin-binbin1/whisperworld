package com.example.whisperworld.entity;

import javax.persistence.*;

@Entity
@Table(name = "friends")
public class Friend {

    @Id
    @Column(name = "UserID")
    private int userId;

    @Column(name = "FriendID")
    private int friendId;

    @ManyToOne
    @JoinColumn(name = "UserID", referencedColumnName = "UserID", insertable = false, updatable = false)
    private User user;

    public Friend() {
    }

    public Friend(int userId, int friendId) {
        this.userId = userId;
        this.friendId = friendId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getFriendId() {
        return friendId;
    }

    public void setFriendId(int friendId) {
        this.friendId = friendId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
