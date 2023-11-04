package com.example.whisperworld.entity;

import java.io.Serializable;
import java.util.Objects;

public class PrivateMessageId implements Serializable {
    private int userID;
    private int receiverID;
    private int messageContentID;

    // 构造函数、equals 和 hashCode 方法

    public PrivateMessageId() {
    }

    public PrivateMessageId(int userID, int receiverID, int messageContentID) {
        this.userID = userID;
        this.receiverID = receiverID;
        this.messageContentID = messageContentID;
    }

    // Getter 和 Setter 方法

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getReceiverID() {
        return receiverID;
    }

    public void setReceiverID(int receiverID) {
        this.receiverID = receiverID;
    }

    public int getMessageContentID() {
        return messageContentID;
    }

    public void setMessageContentID(int messageContentID) {
        this.messageContentID = messageContentID;
    }

    // equals 和 hashCode 方法

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PrivateMessageId that = (PrivateMessageId) o;
        return userID == that.userID && receiverID == that.receiverID && messageContentID == that.messageContentID;
    }

    @Override
    public int hashCode() {
        return Objects.hash(userID, receiverID, messageContentID);
    }
}
