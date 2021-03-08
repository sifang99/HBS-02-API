package com.sifang.service.impl;

import com.sifang.mapper.NumberMessageMapper;
import com.sifang.pojo.NumberMessage;
import com.sifang.pojo.ReturnMessage;
import com.sifang.service.NumberMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Date;
import java.sql.Time;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class NumberMessageServiceImpl implements NumberMessageService {

    @Autowired
    private NumberMessageMapper numberMessageMapper;


    @Transactional
    @Override
    public ReturnMessage addNumberMessage(Map<String, Object> schedules) {
        ReturnMessage returnMessage = new ReturnMessage();
        int dept = Integer.parseInt((String)schedules.get("dept"));
        String doctorNum = (String)schedules.get("doctorNum");
        List<Map<String, Object>> numberMessageList = (List<Map<String, Object>>) schedules.get("schedules");
        NumberMessage numberMessage = new NumberMessage();
        int length = numberMessageList.size();
        int account = 0;

        for(int i = 0; i < length; i++){
            numberMessage.setRemain(0);
            numberMessage.setStatus(0);
            numberMessage.setDoctorNum(doctorNum);
            numberMessage.setDept(dept);
            numberMessage.setPlace((String)numberMessageList.get(i).get("place"));
            numberMessage.setTotal((int) numberMessageList.get(i).get("total"));
            numberMessage.setFee((int) numberMessageList.get(i).get("fee"));
            numberMessage.setTimeInterval((int) numberMessageList.get(i).get("timeInterval"));

            //将String字符串转换成日期
            Date date = Date.valueOf((String) numberMessageList.get(i).get("numberDate"));
            numberMessage.setNumberDate(date);
            //将String字符串转换成Time类型数据
            Time endTime = Time.valueOf(numberMessageList.get(i).get("endTime")+":00");
            numberMessage.setEndTime(endTime);
            Time startTime = Time.valueOf(numberMessageList.get(i).get("startTime")+":00");
            numberMessage.setStartTime(startTime);
            System.out.println(numberMessage);
            account = account + this.numberMessageMapper.addNumberMessage(numberMessage);
        }
        if (account == length){
            returnMessage.setIsSuccess(0);
            returnMessage.setMessage("添加成功！");
        }else{
            returnMessage.setIsSuccess(1);
            returnMessage.setMessage("添加失败！");
        }
        return returnMessage;
    }

    @Override
    public Map<String, Object> getNumberByDept(int dept) {
        Calendar calendar = Calendar.getInstance();
        java.sql.Date dateList[] = new java.sql.Date[7];
        //获取未来7天的日期
        for (int i = 0; i < 7; i++){
            calendar.add(Calendar.DATE, 1);
            String dateStr = String.valueOf(calendar.get(Calendar.YEAR));
            int month = calendar.get(Calendar.MONTH) + 1;
            if (month >= 10){
                dateStr = dateStr + "-" + String.valueOf(month);
            }else {
                dateStr = dateStr + "-0" + String.valueOf(month);
            }

            int date = calendar.get(Calendar.DATE);
            if (date >= 10){
                dateStr = dateStr + "-" + String.valueOf(date);
            }else{
                dateStr = dateStr + "-0" + String.valueOf(date);
            }
            dateList[i] = Date.valueOf(dateStr);
        }
        //获得未来七天的号源信心
        List<NumberMessage> numberMessageList = this.numberMessageMapper.getNumberByDept(26,dateList[0]);
        numberMessageList.addAll(this.numberMessageMapper.getNumberByDept(26, dateList[1]));
        numberMessageList.addAll(this.numberMessageMapper.getNumberByDept(26,dateList[2]));
        numberMessageList.addAll(this.numberMessageMapper.getNumberByDept(26, dateList[3]));
        numberMessageList.addAll(this.numberMessageMapper.getNumberByDept(26,dateList[4]));
        numberMessageList.addAll(this.numberMessageMapper.getNumberByDept(26, dateList[5]));
        numberMessageList.addAll(this.numberMessageMapper.getNumberByDept(26,dateList[6]));
        System.out.println(numberMessageList);

        Map<String, Object> result = new HashMap<>();
        result.put("dateList", dateList);
        result.put("numberMessageList",numberMessageList);
        return result;
    }
}
