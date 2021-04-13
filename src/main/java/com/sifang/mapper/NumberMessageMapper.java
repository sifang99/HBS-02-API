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
    //通过医生编号、日期获得未来的号源信息,范返回值为List
    List<NumberMessage> getNumberListByDocDate(String doctorNum, Date numberDate);
    NumberMessage getNumberByDocDate(String doctorNum, Date numberDate);
    //通过医生编号，日期获得当天的号源信息
    NumberMessage searchNumber(String doctorNum, Date numberDate);
    //通过医生编号查询号源信息
    List<NumberMessage> getNumberByDoctor(String doctorNum);
    //通过号源的id更新号源表
    int updateNumberById(NumberMessage numberMessage);
    //通过号源id删除号源
    int deleteNumberById(int id);
    //通过号源id搜索号源
    NumberMessage getNumberById(int id);
    //预约
    int order(int numberId);
    int setStatus(int status, int numberId);
}
