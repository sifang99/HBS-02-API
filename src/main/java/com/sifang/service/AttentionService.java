package com.sifang.service;

import com.sifang.pojo.Attention;
import com.sifang.pojo.ReturnMessage;

import java.sql.Date;
import java.util.List;

public interface AttentionService {
    ReturnMessage addAttention(Attention attention);
    ReturnMessage updateAttention(Attention attention);
    ReturnMessage deleteAttention(int id[]);
    List<Attention> getAttentionList(Date publishDay);
}
