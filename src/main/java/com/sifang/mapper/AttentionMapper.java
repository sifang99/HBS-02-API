package com.sifang.mapper;

import com.sifang.pojo.Attention;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface AttentionMapper {
    int addAttention(Attention attention);
    int updateAttention(Attention attention);
    int deleteAttention(int id);
}
