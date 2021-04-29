package com.sifang.mapper;

import com.sifang.pojo.SysToken;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface SysTokenMapper {
    int add(SysToken sysToken);
    SysToken getTokenById(int userId);
    int update(SysToken sysToken);
    SysToken getByToken(String token);
}
