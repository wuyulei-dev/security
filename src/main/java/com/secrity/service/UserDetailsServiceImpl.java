/*
 * @(#)UserDetailsServiceImpl.java
 * Copyright (C) 2020 Neusoft Corporation All rights reserved.
 *
 * VERSION        DATE       BY              CHANGE/COMMENT
 * ----------------------------------------------------------------------------
 * @version 1.00  2023年3月22日 wwp-pc          初版
 *
 */
package com.secrity.service;

import java.util.List;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.secrity.dataset.UserDetailsImpl;
import com.secrity.entity.SysUser;
import com.secrity.mapper.MenuMapper;
import com.secrity.mapper.UserMapper;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{

    @Autowired
    private UserMapper userMapper;
    
    @Autowired
    private MenuMapper menuMapper;
    
    @Override
    public UserDetails loadUserByUsername(
        String username) throws UsernameNotFoundException {
        //根据用户名查询用户信息
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUser::getLoginName, username);
        SysUser sysUser = userMapper.selectOne(queryWrapper);
        
        //如果查询不到数据就抛出异常，由全局异常处理
        if(Objects.isNull(sysUser)) {
            throw new UsernameNotFoundException("用户名或密码错误");
        }
        
        //封装UserDetails对象并返回
        List<String> permissions = menuMapper.selectPermsByUserid(sysUser.getUserId());
        UserDetailsImpl userDetail = new UserDetailsImpl(sysUser,permissions);
        return userDetail;
    }

}
