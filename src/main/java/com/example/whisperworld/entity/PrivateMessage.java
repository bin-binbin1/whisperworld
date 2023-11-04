package com.example.whisperworld.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "private_messages")
public class PrivateMessage {

    @EmbeddedId
    private PrivateMessageId id;

    @Column(name = "MessageContent")
    private String messageContent;

    @Column(name = "SendTime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date sendTime;

    @Column(name = "ReceiveState")
    private boolean receiveState;

    @ManyToOne
    @JoinColumn(name = "UserID", referencedColumnName = "UserID", insertable = false, updatable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "ReceiverID", referencedColumnName = "UserID", insertable = false, updatable = false)
    private User receiver;

    public PrivateMessage() {
    }

    public PrivateMessage(PrivateMessageId id, String messageContent, Date sendTime, boolean receiveState, User user, User receiver) {
        this.id = id;
        this.messageContent = messageContent;
        this.sendTime = sendTime;
        this.receiveState = receiveState;
        this.user = user;
        this.receiver = receiver;
    }

    public PrivateMessageId getId() {
        return id;
    }

    public void setId(PrivateMessageId id) {
        this.id = id;
    }

    public String getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    public boolean isReceiveState() {
        return receiveState;
    }

    public void setReceiveState(boolean receiveState) {
        this.receiveState = receiveState;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getReceiver() {
        return receiver;
    }

    public void setReceiver(User receiver) {
        this.receiver = receiver;
    }
}
