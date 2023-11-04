package com.example.whisperworld.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "crowds_messages")
public class CrowdMessage {

    @Id
    @Column(name = "GroupMessageID")
    private int groupMessageId;

    @Column(name = "ConversationContent")
    private String conversationContent;

    @Column(name = "SendTime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date sendTime;

    @ManyToOne
    @JoinColumn(name = "GroupID", referencedColumnName = "GroupID")
    private Crowds crowd;

    @ManyToOne
    @JoinColumn(name = "UserID", referencedColumnName = "UserID")
    private User user;

    public CrowdMessage() {
    }

    public CrowdMessage(int groupMessageId, String conversationContent, Date sendTime, Crowds crowd, User user) {
        this.groupMessageId = groupMessageId;
        this.conversationContent = conversationContent;
        this.sendTime = sendTime;
        this.crowd = crowd;
        this.user = user;
    }

    public int getGroupMessageId() {
        return groupMessageId;
    }

    public void setGroupMessageId(int groupMessageId) {
        this.groupMessageId = groupMessageId;
    }

    public String getConversationContent() {
        return conversationContent;
    }

    public void setConversationContent(String conversationContent) {
        this.conversationContent = conversationContent;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    public Crowds getCrowd() {
        return crowd;
    }

    public void setCrowd(Crowds crowd) {
        this.crowd = crowd;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
