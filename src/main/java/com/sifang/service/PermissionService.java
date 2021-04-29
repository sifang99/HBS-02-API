package com.sifang.service;

import com.sifang.pojo.Permission;

public interface PermissionService {
    int addPermission(String name);
    Permission getById(int id);
}
