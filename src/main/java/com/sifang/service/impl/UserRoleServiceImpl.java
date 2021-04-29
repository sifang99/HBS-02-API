package com.sifang.service.impl;

import com.sifang.mapper.UserRoleMapper;
import com.sifang.pojo.UserRole;
import com.sifang.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserRoleServiceImpl implements UserRoleService {
    @Autowired
    private UserRoleMapper userRoleMapper;

    @Override
    public List<UserRole> getRoleListByUser(int userId) {
        return userRoleMapper.getRoleListByUser(userId);
    }

    @Override
    public int add(UserRole userRole) {
        return userRoleMapper.add(userRole);
    }
}
