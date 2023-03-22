/*
 * @(#)LoginService.java
 * Copyright (C) 2020 Neusoft Corporation All rights reserved.
 *
 * VERSION        DATE       BY              CHANGE/COMMENT
 * ----------------------------------------------------------------------------
 * @version 1.00  2022年8月31日 wwp-pc          初版
 *
 */
package com.secrity.service;

import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import com.secrity.dataset.Result;

@Service
public class LoginService {
    
    @Autowired
    private AuthenticationManager authenticationManager;
    
    /**
     * 登录认证
     * @param userName
     * @param pass
     * @return
     */
    public Result login(String userName,String pass) {
      // 1 创建 UsernamePasswordAuthenticationToken未认证状态
      UsernamePasswordAuthenticationToken usernameAuthentication = new UsernamePasswordAuthenticationToken(userName, pass);
      // 2 认证 返回已认证状态Authentication
      Authentication authentication = this.authenticationManager.authenticate(usernameAuthentication);
      //判断认证是否通过  通过：authenticate!=null 不通过：authenticate=null
      if(Objects.isNull(authentication)) {
          throw   new RuntimeException("认证失败！");
      }
     
      return  Result.success().data(authentication);
    }

}
