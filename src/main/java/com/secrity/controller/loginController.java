/*
 * @(#)loginController.java
 * Copyright (C) 2020 Neusoft Corporation All rights reserved.
 *
 * VERSION        DATE       BY              CHANGE/COMMENT
 * ----------------------------------------------------------------------------
 * @version 1.00  2022年6月14日 wwp-pc          初版
 *
 */
package com.secrity.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.secrity.dataset.Result;
import com.secrity.dataset.UserDetailsImpl;
import com.secrity.service.LoginService;

@Controller
public class loginController {

    @Autowired
    private LoginService loginService;

    
    @RequestMapping("/test")
    public void test(
        HttpServletResponse response) {
        System.out.println("测试通过");

    }
    
    /**
     * 自定义登录逻辑
     * 
     * @param userName
     * @param pass
     * @return
     */
    @PostMapping("/doLogin")
    public String jwtLogin(
        HttpServletRequest request,
        String userName,
        String passWord) {
        Result result = loginService.login(userName, passWord);
        if (result.getSuccess()) {
            //验证成功
            Authentication authentication = (Authentication) result.getData();
            //存入SecurityContextHolder 表明用户已认证，此次请求内访问其他受保护资源不会收到拦截
            SecurityContextHolder.getContext().setAuthentication(authentication);
            //存入session    下次访问时，过滤器链获取已认证authentication就不再认证
            HttpSession session = request.getSession(false);
            if (session != null) {
                session.invalidate();
            }
            session = request.getSession(true);
            //在session中存放security context,方便同一个session中控制用户的其他操作
            session.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());
        }
        return "redirect:/index";
    }

    @RequestMapping("/sucess")
    public String login(
        HttpServletResponse response) {
        System.out.println("登录成功");
        //访问HTML文件，它并不支持响应头带有 post 的应答包，所以会报错，解决：加redirect重定向直接请求html页面
        return "redirect:/index"; //绝对路径：相对于项目根路径+/main.html -> http://127.0.0.1:8080/sc/main.html

    }

    @RequestMapping("/index")
    public String index(
        HttpServletResponse response) {
        System.out.println("sdfdsfd");
        return "/main.html"; //绝对路径：相对于项目根路径+/main.html -> http://127.0.0.1:8080/sc/main.html
    }
}
