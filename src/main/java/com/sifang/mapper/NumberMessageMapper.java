package com.sifang.mapper;

import com.sifang.pojo.NumberMessage;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.LinkedList;
import java.util.List;

@Mapper
@Repository
public interface NumberMessageMapper {
    List<NumberMessage> getNumerMessageList();
    int addNumberMessage(NumberMessage numberMessage);
    //通过科室以及日期获得号源信息
    LinkedList<NumberMessage> getNumberByDept(int dept, Date numberDate);
}
