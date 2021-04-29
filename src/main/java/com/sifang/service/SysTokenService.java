package com.sifang.service;

import com.sifang.pojo.SysToken;

public interface SysTokenService {
    int add(SysToken sysToken);
    SysToken getTokenById(int userId);
    int update(SysToken sysToken);
    SysToken getByToken(String token);
}
