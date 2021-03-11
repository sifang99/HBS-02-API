package com.sifang.service.impl;

import com.sifang.mapper.NumberMessageMapper;
import com.sifang.pojo.Doctor;
import com.sifang.pojo.NumberMessage;
import com.sifang.pojo.ReturnMessage;
import com.sifang.service.DoctorService;
import com.sifang.service.NumberMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Date;
import java.sql.Time;
import java.util.*;

@Service
public class NumberMessageServiceImpl implements NumberMessageService {

    @Autowired
    private NumberMessageMapper numberMessageMapper;
    @Autowired
    private DoctorService doctorService;


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
            numberMessage.setStatus(0);
            numberMessage.setDoctorNum(doctorNum);
            numberMessage.setDept(dept);
            numberMessage.setRemain((int) numberMessageList.get(i).get("total"));
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
    public List<NumberMessage> getNumber(int dept, Date date) {
        List<NumberMessage> numberMessageList = this.numberMessageMapper.getNumberByDept(dept, date);
        int length = numberMessageList.size();
        for (int i = 0; i < length; i++){
            int status = numberMessageList.get(i).getStatus();
            if (status == 2 || status == 3){
                numberMessageList.remove(i);
                length --;
                i--;
            }
        }
        return numberMessageList;
    }

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

    @Override
    public NumberMessage getNumberByDocDate(String doctorNumber, Date numberDate) {
        return this.numberMessageMapper.getNumberByDocDate(doctorNumber, numberDate);
    }

    @Override
    public List<NumberMessage> getUsefulNumberByDoctor(String doctorNum) {
        List<NumberMessage> numberMessageLinkedList = this.numberMessageMapper.getNumberByDoctor(doctorNum);
        int length = numberMessageLinkedList.size();
        for (int i = 0; i < length; i++){
            int status = numberMessageLinkedList.get(i).getStatus();
            if (status == 2 || status == 3){
                numberMessageLinkedList.remove(i);
                length --;
                i--;
            }
        }
        return numberMessageLinkedList;
    }

    @Override
    public ReturnMessage updateNumberById(NumberMessage numberMessage) {
        ReturnMessage returnMessage = new ReturnMessage();
        if (this.numberMessageMapper.updateNumberById(numberMessage) >= 1){
            returnMessage.setIsSuccess(0);
            returnMessage.setMessage("修改成功！");
        }else{
            returnMessage.setIsSuccess(1);
            returnMessage.setMessage("修改失败！");
        }
        return returnMessage;
    }

    @Override
    public List<NumberMessage> getNumberByDeptDate(int dept, Date date) {
        LinkedList<NumberMessage> numberMessageLinkedList = this.numberMessageMapper.getNumberByDept(dept, date);
        int length = numberMessageLinkedList.size();
        for(int i = 0; i < length; i++){
            if (numberMessageLinkedList.get(i).getStatus() == 0 ||
                numberMessageLinkedList.get(i).getStatus() == 1){
                numberMessageLinkedList.remove(i);
                length--;
                i--;
            }
        }
        return numberMessageLinkedList;
    }

    @Transactional
    @Override
    public ReturnMessage deleteNumberById(int[] id) {
        int length = id.length;
        int count = 0;
        ReturnMessage returnMessage = new ReturnMessage();
        for (int i = 0; i < length; i++){
            count = count + this.numberMessageMapper.deleteNumberById(id[i]);
        }
        if (count == length){
            returnMessage.setIsSuccess(0);
            returnMessage.setMessage("添加成功！");
        }else{
            returnMessage.setIsSuccess(1);
            returnMessage.setMessage("添加失败！");
        }
        return returnMessage;
    }

    @Override
    public Map<String, Integer>[] getFutureDate() {
        Calendar calendar = Calendar.getInstance();
        Map<String, Integer> dateList[] = new HashMap[7];
        //获取未来7天的日期
        for (int i = 0; i < 7; i++){
            calendar.add(Calendar.DATE, 1);
            Map<String, Integer> dateStr = new HashMap<>();
            dateStr.put("year", calendar.get(Calendar.YEAR));
            int month = calendar.get(Calendar.MONTH) + 1;
            int date = calendar.get(Calendar.DATE);
            dateStr.put("month", month);
            dateStr.put("date", date);
            dateList[i] = dateStr;
        }
        return dateList;
    }

    @Override
    public List<Doctor> getDoctorsByDeptDate(int dept, Date date) {
        List<NumberMessage> numberMessageList = this.getNumber(dept, date);
        int length = numberMessageList.size();
        List<Doctor> doctorList = new ArrayList<>();
        for(int i = 0; i < length; i++){
            String doctorNum = numberMessageList.get(i).getDoctorNum();
            doctorList.add(this.doctorService.getDoctorByNum(doctorNum));
        }
        return doctorList;
    }
}
