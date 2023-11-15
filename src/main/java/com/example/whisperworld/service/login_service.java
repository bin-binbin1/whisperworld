package com.example.whisperworld.service;

import com.example.whisperworld.entity.User;
import com.example.whisperworld.mapper.login_mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.servlet.http.HttpSession;
import java.awt.desktop.SystemEventListener;
import java.util.ArrayList;

@Service
public class login_service implements UserDetailsService {

    private final login_mapper loginMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    public login_service(login_mapper loginMapper){
        this.loginMapper = loginMapper;
    }


    public Integer getLoginID(String username){
        return loginMapper.login_id(username);
    }


    protected void ssss(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("user").password("password").roles("ROLE_USER")
                .and()
                .withUser("admin").password("password").roles("ROLE_ADMIN");
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 通过用户名获取用户信息
        User user = loginMapper.login_pwd(Integer.parseInt(username));

        if (user == null) {
            // 如果用户不存在，返回一个具有特殊权限的UserDetails对象
            return new org.springframework.security.core.userdetails.User("", "", new ArrayList<>());
        }

        return new org.springframework.security.core.userdetails.User(user.getUserID().toString(), user.getUserPassword(), new ArrayList<>());
    }
}
