package com.sifang.service;

import com.sifang.pojo.Attention;
import com.sifang.pojo.ReturnMessage;
import com.sifang.pojo.SysToken;

import java.sql.Date;
import java.util.List;
import java.util.Map;

public interface AttentionService {
    ReturnMessage addAttention(Attention attention);
    ReturnMessage updateAttention(Attention attention);
    ReturnMessage deleteAttention(int id[]);
    List<Attention> getAttentionList(Date publishDay);
    List<Map<String, Object>> getAllAttentions();
    Attention getAttentionById(int id);

}
