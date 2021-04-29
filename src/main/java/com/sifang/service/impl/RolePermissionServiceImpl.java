package com.sifang.service.impl;

import com.sifang.mapper.RolePermissionMapper;
import com.sifang.pojo.RolePermission;
import com.sifang.service.RolePermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RolePermissionServiceImpl implements RolePermissionService {
    @Autowired
    private RolePermissionMapper rolePermissionMapper;

    @Override
    public int add(RolePermission rolePermission) {
        return rolePermissionMapper.add(rolePermission);
    }

    @Override
    public List<RolePermission> getByRole(int roleId) {
        return rolePermissionMapper.getByRole(roleId);
    }
}
