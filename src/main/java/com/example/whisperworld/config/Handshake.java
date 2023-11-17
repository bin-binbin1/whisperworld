package com.example.whisperworld.config;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.lang.Nullable;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;
import javax.servlet.http.HttpSession;
import java.util.Map;

//
//public class Handshake implements HandshakeInterceptor {
//
//    @Override
//    public boolean beforeHandshake(org.springframework.http.server.ServerHttpRequest request, ServerHttpResponse response,
//                                   WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
//        if (request instanceof ServletServerHttpRequest) {
//            ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) request;
//            HttpSession httpSession = servletRequest.getServletRequest().getSession();
//
//            // 将HttpSession中的数据放入WebSocketSession的属性中
//            attributes.put("loginID", httpSession.getAttribute("loginID"));
//        }
//        return true;
//    }
//
//    @Override
//    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response,
//                               WebSocketHandler wsHandler, @Nullable Exception exception) {
//        // 连接结束后，清空WebSocketSession中的属性
//
//    }
//}