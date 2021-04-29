package com.sifang.service;

import com.sifang.pojo.RolePermission;

import java.util.List;

public interface RolePermissionService {
    int add(RolePermission rolePermission);
    List<RolePermission> getByRole(int roleId);
}
