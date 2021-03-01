package com.sifang.mapper;

import com.sifang.pojo.NumberMessage;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface NumberMessageMapper {
    List<NumberMessage> getNumerMessageList();
}
