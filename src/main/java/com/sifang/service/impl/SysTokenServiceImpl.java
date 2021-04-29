package com.sifang.service.impl;

import com.sifang.mapper.SysTokenMapper;
import com.sifang.pojo.SysToken;
import com.sifang.service.SysTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysTokenServiceImpl implements SysTokenService {
    @Autowired
    private SysTokenMapper sysTokenMapper;
    @Override
    public int add(SysToken sysToken) {
        return sysTokenMapper.add(sysToken);
    }

    @Override
    public SysToken getTokenById(int userId) {
        return sysTokenMapper.getTokenById(userId);
    }

    @Override
    public int update(SysToken sysToken) {
        return sysTokenMapper.update(sysToken);
    }

    @Override
    public SysToken getByToken(String token) {
        return sysTokenMapper.getByToken(token);
    }
}
