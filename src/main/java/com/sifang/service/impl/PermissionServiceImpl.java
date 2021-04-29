package com.sifang.service.impl;

import com.sifang.mapper.PermissionMapper;
import com.sifang.pojo.Permission;
import com.sifang.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PermissionServiceImpl implements PermissionService {
    @Autowired
    private PermissionMapper permissionMapper;

    @Override
    public int addPermission(String name) {
        return permissionMapper.addPermission(name);
    }

    @Override
    public Permission getById(int id) {
        return permissionMapper.getById(id);
    }
}
