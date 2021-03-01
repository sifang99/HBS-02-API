package com.sifang.service;

import com.sifang.pojo.Attention;
import com.sifang.pojo.ReturnMessage;

public interface AttentionService {
    ReturnMessage addAttention(Attention attention);
    ReturnMessage updateAttention(Attention attention);
    ReturnMessage deleteAttention(int id);
}
