package com.sifang.mapper;

import com.sifang.pojo.UserRole;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface UserRoleMapper {
    List<UserRole> getRoleListByUser(int userId);
    int add(UserRole userRole);
}
