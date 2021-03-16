package com.sifang.service;

import com.sifang.pojo.Doctor;
import com.sifang.pojo.NumberMessage;
import com.sifang.pojo.ReturnMessage;

import javax.print.Doc;
import java.sql.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public interface NumberMessageService {
    public ReturnMessage addNumberMessage(Map<String, Object> schedules);
    //根据科室id、日期查询可预约信息
    public List<NumberMessage> getNumber(int dept, Date date);
    //根据医生编号、日期判断医生是否已排班，并返回排班信息
    NumberMessage  getNumberByDocDate(String doctorNumber, Date numberDate);
    //根据医生编号查询排班信息,只返回号源状态为可预约、已约满状态的号源
    List<NumberMessage> getUsefulNumberByDoctor(String doctorNum);
    ReturnMessage updateNumberById(NumberMessage numberMessage);
    //根据科室id以及日期，返回处于停诊、已过期状态的号源
    List<NumberMessage> getNumberByDeptDate(int dept, Date date);
    //通过科室id批量删除号源信息
    ReturnMessage deleteNumberById(int id[]);
    //获得未来7天的日期
    Map<String, Integer>[]  getFutureDate();
    //根据科室id、日期获得有排班的医生信息
    List<Doctor> getDoctorsByDeptDate(int dept, Date date);
}
