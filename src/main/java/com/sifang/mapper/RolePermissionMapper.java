package com.sifang.mapper;

import com.sifang.pojo.RolePermission;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface RolePermissionMapper {
    int add(RolePermission rolePermission);
    List<RolePermission> getByRole(int roleId);
}
