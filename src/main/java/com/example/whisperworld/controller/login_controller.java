package com.example.whisperworld.controller;

import com.example.whisperworld.entity.User;
import com.example.whisperworld.service.login_service;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@RestController
public class login_controller {

    login_service loginService;
    private final AuthenticationManager authenticationManager;
    @Autowired
    public login_controller(login_service loginService,AuthenticationManager authenticationManager){
        this.loginService=loginService;
        this.authenticationManager=authenticationManager;
    }

    @PostMapping("/login/submit")
    public ResponseEntity<String> Login(@RequestBody User user, HttpSession session){
        user.setUserID(loginService.getLoginID(user.getUserName()));
        System.out.println(user);

        Map<String,Object> response = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();
        String json="";
        try {
            // 创建UsernamePasswordAuthenticationToken
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(user.getUserID(), user.getUserPassword());
            // 使用AuthenticationManager来验证这个token
            Authentication auth = authenticationManager.authenticate(token);
            // 如果验证成功，将Authentication对象设置到SecurityContext中
            SecurityContextHolder.getContext().setAuthentication(auth);
            //检索经过身份验证的用户的UserDetails对象
            UserDetails userDetails = (UserDetails) auth.getPrincipal();
            //检查用户是否为管理员
            boolean isAdmin = userDetails.getAuthorities().stream()
                    .anyMatch(role -> role.getAuthority().equals("ROLE_ADMIN"));

            session.setAttribute("loginID",user.getUserID());
            session.setAttribute("isAdmin",isAdmin);

            //如果验证成功
            response.put("isAdmin",isAdmin);
            response.put("authenticated",true);
//            response.put("isAdmin",isAdmin);
        } catch (AuthenticationException e) {
            // 如果验证失败，可以返回到登录页面
            response.put("authenticated",false);
        }
        try {
            json = mapper.writeValueAsString(response); // 将Map对象转换为JSON字符串
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return new ResponseEntity<>(json, HttpStatus.OK);
    }
}
