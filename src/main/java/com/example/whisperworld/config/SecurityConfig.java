package com.example.whisperworld.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/logout","/public/**","/","/game","/welcome","/login", "/login/submit","/register_submit2","/websocket-endpoint/**","/css/**","/pictures/**").permitAll() // 允许公开访问的URL
                //.antMatchers("/private/**").hasRole("ROLE_ADMIN")//只有管理员才能访问admin界面
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login") // 自定义登录页面的路径
                .permitAll()
                .and()
                .logout()
                .permitAll();
        http.csrf().ignoringAntMatchers("/register_submit2","/login/submit","/api/submitComment","/api/likeTopic","/api/sendtopic",
                "/api/friendApply",
                "/api/handleFriendRequest",
                "/admin_announce/post_note",
                "/api/handleGroupRequest",
                "/api/createGroup",
                "/api/dismissGroup",
                "/api/leaveGroup",
                "/api/groupApply",
                "/websocket-endpoint/**",
                "/novel/addBookmark"
        );//Post
    }

    // 其他配置...
}
