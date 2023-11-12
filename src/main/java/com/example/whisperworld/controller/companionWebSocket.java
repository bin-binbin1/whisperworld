package com.example.whisperworld.controller;

import com.example.whisperworld.service.companionService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

@Controller
@ServerEndpoint("/websocket-endpoint/{userId}")
public class companionWebSocket extends TextWebSocketHandler {
    private Session session;
    private Integer userID;
    private static CopyOnWriteArraySet<companionWebSocket> webSockets =new CopyOnWriteArraySet<>();
    // 用来存在线连接用户信息
    private static ConcurrentHashMap<String,Session> sessionPool = new ConcurrentHashMap<String,Session>();
    @Autowired
    private companionService service;

    @OnOpen
    public void onOpen(Session session, @PathParam(value="userId") Integer userId) {
        try {
            this.session = session;
            this.userID = userId;
            webSockets.add(this);
            session.getUserProperties().put("userID", userId);
            sessionPool.put(userID.toString(), session);

            System.out.println("&#8203;``【oaicite:0】``&#8203;有新的连接，总数为:" + webSockets.size());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnClose
    public void onClose() {
        webSockets.remove(this);
        sessionPool.remove(this.userID);
        System.out.println("【websocket消息】连接断开，总数为:"+webSockets.size());
    }

    @OnMessage
    public void onMessage(String message) {
        Integer userID = (Integer) session.getUserProperties().get("userID");
        System.out.println("【websocket消息】收到用户" + userID + "的消息:" + message);
        // 解析消息字符串
        String[] parts = message.split("/");
        if(!parts[0].equals("send")){
            return;
        }
        String requestType = parts[1];  // 获取请求类型
        String data = parts[2];  // 获取数据
        Integer receiverID;
        // 根据请求类型处理消息
        switch (requestType) {
            case "getFriends":
                // 处理getFriends请求
                sendOneMessage(userID,namesToString( service.getFriendsByName(userID,data)));
                break;
            case "getHistory":
                // 处理getHistory请求
                receiverID=service.getNameByID(data);
                session.getUserProperties().put("friendID",receiverID);
                sendOneMessage(userID, service.getMessages(userID,receiverID));
                break;
            case "sendMessages":
                receiverID=(Integer) session.getUserProperties().get("friendID");
                if(service.sendMessage(data,userID,receiverID)){

                }
                break;
            case "getAllFriends":
                sendOneMessage(userID,namesToString(service.getAllFriends(userID)));
                break;
            default:
                System.out.println("未知的请求类型: " + requestType);
                break;
        }
    }


    @OnError
    public void onError(Session session, Throwable error) {
        System.out.println("用户错误,原因:"+error.getMessage());
        error.printStackTrace();
    }

    public void sendOneMessage(Integer userID, String message) {
        Session session = sessionPool.get(userID);
        if (session != null&&session.isOpen()) {
            try {
                System.out.println("【websocket消息】 单点消息:"+message);
                session.getAsyncRemote().sendText(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    private String namesToString(List<String> names){
        List<Map<String,Object>> responses = new ArrayList<>();
        for(String name : names){
            Map<String,Object> response = new HashMap<>();
            response.put("friendNames",name);
            responses.add(response);
        }
        ObjectMapper mapper = new ObjectMapper();
        String json="";
        try {
            json = mapper.writeValueAsString(responses);
        }catch (JsonProcessingException e){
            e.printStackTrace();
        }
        return json;
    }

}
