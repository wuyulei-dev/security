/*
 * @(#)WebsecurityConfig.java
 * Copyright (C) 2020 Neusoft Corporation All rights reserved.
 *
 * VERSION        DATE       BY              CHANGE/COMMENT
 * ----------------------------------------------------------------------------
 * @version 1.00  2023年3月22日 wwp-pc          初版
 *
 */
package com.secrity.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class WebsecurityConfig extends WebSecurityConfigurerAdapter{

    //密码器
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    //AuthenticationManager
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }


    @Override
    public void configure(
        WebSecurity web) throws Exception {
        super.configure(web);
    }

    @Override
    protected void configure(
        HttpSecurity http) throws Exception {
        http.formLogin()
                .loginPage("/login.html") //自定义登录页
                //自定义登录controller，不能配置该路径，不然会被UsernamePasswordAuthenticationFilter拦截认证处理
                //配置.permitAll()也会被拦截
                //.loginProcessingUrl("/doLogin").permitAll()      //登录请求接口（路径为去掉应用路径的相对路径）
                .successForwardUrl("/sucess")      //认证成功后跳转url，要有对应的controller方法处理
                .permitAll()
                .and()
            .authorizeRequests()
                .antMatchers("/login.html","/test","/doLogin").permitAll()    //登录页放行
                .antMatchers("/doLogin").anonymous()    //登录请求匿名访问
                .anyRequest().authenticated()
            .and()
            .csrf().disable();
    }
}
