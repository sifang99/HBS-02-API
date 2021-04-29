package com.sifang.service.impl;

import com.sifang.mapper.RoleMapper;
import com.sifang.pojo.Role;
import com.sifang.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleMapper roleMapper;

    @Override
    public Role getRoleById(int id) {
        return roleMapper.getRoleById(id);
    }
}
