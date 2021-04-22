package com.sifang.mapper;

import com.sifang.pojo.Attention;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Mapper
@Repository
public interface AttentionMapper {
    int addAttention(Attention attention);
    int updateAttention(Attention attention);
    int deleteAttention(int id);
    List<Attention> getAttentionListByDate(Date publishDay);
    List<Attention> getAttentionList();
    Attention getAttentionById(int id);
}
