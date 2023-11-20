package com.example.whisperworld.service;

import com.example.whisperworld.entity.Supervisor;
import com.example.whisperworld.entity.User;
import com.example.whisperworld.mapper.login_mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
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
import javax.sql.DataSource;
import java.awt.desktop.SystemEventListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class login_service implements UserDetailsService {

    private final login_mapper loginMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private DataSource dataSource;

    @Autowired
    public login_service(login_mapper loginMapper){
        this.loginMapper = loginMapper;
    }


    public Integer getLoginID(String username){
        return loginMapper.login_id(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 通过用户名获取用户信息
        User user = loginMapper.login_pwd(Integer.parseInt(username));//密码&ID
        Supervisor supervisor = loginMapper.loginSuper(Integer.parseInt(username));//判断是否为管理员
        if (user == null && supervisor == null){
            throw new UsernameNotFoundException("用户不存在");
        }
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        loginMapper.onlineState(username);
        if(supervisor != null){
            authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        }

        UserDetails userDetails = new org.springframework.security.core.userdetails.User(
                user.getUserID().toString(),
                user.getUserPassword(),
                authorities
        );

        return userDetails;
    }
}
