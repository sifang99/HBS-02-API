package com.sifang.mapper;

import com.sifang.pojo.Role;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface RoleMapper {
    int addRole(String name);
    Role getRoleById(int id);
}
