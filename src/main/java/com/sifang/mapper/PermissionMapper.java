package com.sifang.mapper;

import com.sifang.pojo.Permission;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface PermissionMapper {
    int addPermission(String name);
    Permission getById(int id);
}
