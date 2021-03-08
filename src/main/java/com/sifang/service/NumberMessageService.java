package com.sifang.service;

import com.sifang.pojo.ReturnMessage;

import java.util.Map;

public interface NumberMessageService {
    public ReturnMessage addNumberMessage(Map<String, Object> schedules);
    //通过科室查询未来7天的号源
    //返回未来七天的日期，以及未来七天的预约号源信息
    public Map<String, Object> getNumberByDept(int dept);
}
