package com.example.whisperworld.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/templates/public/**","/testmysql","/**").permitAll() // 允许公开访问的URL
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login") // 自定义登录页面的路径
                .permitAll()
                .and()
                .logout()
                .permitAll();
    }

    // 其他配置...
}
