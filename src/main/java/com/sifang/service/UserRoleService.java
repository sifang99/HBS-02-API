package com.sifang.service;

import com.sifang.pojo.UserRole;

import java.util.List;

public interface UserRoleService {
    List<UserRole> getRoleListByUser(int userId);
    int add(UserRole userRole);
}
