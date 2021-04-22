package com.sifang.service.impl;

import com.sifang.mapper.OrderMessageMapper;
import com.sifang.pojo.*;
import com.sifang.service.*;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.*;

@Service
public class OrderMessageServiceImpl implements OrderMessageService {
    @Autowired
    private NumberMessageService numberMessageService;
    @Autowired
    private OrderMessageMapper orderMessageMapper;
    @Autowired
    private PatientService patientService;
    @Autowired
    private DoctorService doctorService;
    @Autowired
    private DeptService deptService;
    static int count = 1;

    @Override
    public List<Map<String, String>> getOrderList(String doctorNum, String dateStr) {
        Date date = Date.valueOf(dateStr);
        //默认医生一天中只排一次班
        count = 1;
        NumberMessage numberMessage = numberMessageService.searchNumber(doctorNum, date);
        if(numberMessage.getRemain() == 0){
            return null;
        }
        //分割时间段
        String startTimeStr = numberMessage.getStartTime().toString();
        String endTimeStr = numberMessage.getEndTime().toString();
        String[] startTime = startTimeStr.split(":");
        String[] endTime = endTimeStr.split(":");
        int interval = numberMessage.getTimeInterval();
        int startMin = Integer.parseInt(startTime[0]) * 60 + Integer.parseInt(startTime[1]);
        int endMin = Integer.parseInt(endTime[0])*60 + Integer.parseInt(endTime[1]);
        int totalMin = endMin - startMin;
        int time_remainder = totalMin%interval;
        //共有time_integer个时间段
        int time_integer = totalMin/interval;


        //就诊序号的分割
        int patient_integer = numberMessage.getTotal()/time_integer;
        int patient_remainder = numberMessage.getTotal()%time_integer;

        //开始分割时间
        List<Map<String, String>> result = new ArrayList<>();
        for (int i = 0; i < time_integer; i++){
            int hour = Integer.parseInt(startTime[0]);
            int min = Integer.parseInt(startTime[1]);
            String s;
            if (min < 10){
                s = hour + ":0" + min;
            }else {
                s = hour + ":" + min;
            }
            if (time_remainder > 0){
                if (min + 1 + interval >= 60){
                    min = (min + interval + 1) % 60;
                    hour++;
                }else{
                    min = min + interval + 1;
                }
                time_remainder --;
            }else{
                if (min + interval >= 60){
                    min = (min + interval) % 60;
                    hour++;
                }else{
                    min = min + interval;
                }
            }
            startTime[0] = "" + hour;
            startTime[1] = "" + min;

            String e;
            if (min < 10){
                e = startTime[0] + ":0" + startTime[1];
            }else {
                e = startTime[0] + ":" + startTime[1];
            }

            int length = patient_integer;
            if (patient_remainder > 0){
                length = length + 1;
                patient_remainder --;
            }
            for (int j = 0; j < length; j++){
                Map<String, String> number = new HashMap<>();
                number.put("startTime", s);
                number.put("endTime", e);
                number.put("sequence", "" +count++);
                number.put("fee", "" + numberMessage.getFee());
                result.add(number);
            }
        }

        List<OrderMessage> numberSequence = this.getNumberSequencebyId(numberMessage.getId());
        int a = result.size();
        int b = numberSequence.size();
        for (int i = 0; i < a; i++){
            int resultSequence = Integer.parseInt(result.get(i).get("sequence"));
            for (int j = 0; j < b; j++){
                int numSe = numberSequence.get(j).getNumSequence();
                int status = numberSequence.get(j).getStatus();
                if (resultSequence == numSe && (status == 0 || status == 1 || status == 5)){
                    result.remove(i);
                    a--;
                    i--;
                    break;
                }
            }
        }
        return result;
    }

    @Override
    public List<OrderMessage> getNumberSequencebyId(int numberId) {
        return this.orderMessageMapper.getOrderMessageByNumberId(numberId);
    }

    @Override
    public ReturnMessage addOrderMessage(OrderMessage orderMessage) {
        ReturnMessage returnMessage = new ReturnMessage();
        String account = orderMessage.getPatientID();
        String numberDate = orderMessage.getDetailTime();
        String[] date = numberDate.split("  ");
        List<OrderMessage> orderMessageList = this.orderMessageMapper.getOrderMessageByAccount(account,date[0]);
        int length = orderMessageList.size();
        //过滤掉没有用的预约记录
        for (int i = 0; i < length; i++){
            if (orderMessageList.get(i).getStatus() == 2 || orderMessageList.get(i).getStatus() == 3){
                orderMessageList.remove(i);
                i--;
                length--;
            }
        }
        for (int i = 0; i < length; i++){
            OrderMessage message = orderMessageList.get(i);
            //如果该患者已预约该科室的某位专家，则不能再预约该科室的其他专家
            if (orderMessage.getDept() == message.getDept()){
                returnMessage.setMessage("该患者已预约有该科室的专家");
                returnMessage.setIsSuccess(1);
                return returnMessage;
            }
        }
        //同一患者一天最多预约三个不同的科室
        if (length >= 3){
            returnMessage.setIsSuccess(1);
            returnMessage.setMessage("同一患者一天中最多预约三个不同的科室！");
            return returnMessage;
        }

        if (this.orderMessageMapper.addOrderMessage(orderMessage) >= 1){
            if (this.numberMessageService.order(orderMessage.getNumberId()).getIsSuccess() == 0){
                returnMessage.setIsSuccess(0);
                returnMessage.setMessage("预约成功！");
            }else{
                returnMessage.setIsSuccess(1);
                returnMessage.setMessage("预约失败！");
            }
        }else{
            returnMessage.setIsSuccess(1);
            returnMessage.setMessage("预约失败！");
        }
        return returnMessage;
    }

    @Override
    public List<Map<String, String>> getOrderRecord(int userId) {
        List<OrderMessage> orderMessageList = this.orderMessageMapper.getOrderMessageByUserId(userId);
        int length = orderMessageList.size();
        List<Map<String, String>> resultList = new ArrayList<>();
        for (int i = 0; i < length; i++){
            int numberId = orderMessageList.get(i).getNumberId();
            String patientAccount = orderMessageList.get(i).getPatientID();
            Map<String, String> result = new HashMap<>();
            NumberMessage numberMessage = this.numberMessageService.getNumberById(numberId);
            PatientMessage patientMessage = this.patientService.getPatientByAccount(patientAccount);
            Doctor doctor = this.doctorService.getDoctorByNum(numberMessage.getDoctorNum());

            result.put("dept", ""+(orderMessageList.get(i).getDept()));
            result.put("time", orderMessageList.get(i).getDetailTime());
            result.put("sequence", ""+orderMessageList.get(i).getNumSequence());
            result.put("orderDate", orderMessageList.get(i).getOrderDate().toString());
            result.put("doctor", doctor.getName());
            result.put("place", numberMessage.getPlace());
            result.put("fee", ""+numberMessage.getFee());
            result.put("patient", patientMessage.getName());
            result.put("gender", "" +patientMessage.getGender());
            result.put("status", "" + orderMessageList.get(i).getStatus());
            result.put("id", ""+orderMessageList.get(i).getId());
            result.put("doctorNum", doctor.getNum());

            resultList.add(result);
        }
        return resultList;
    }

    @Override
    public ReturnMessage returnNumber(int id) {
        ReturnMessage returnMessage = new ReturnMessage();
        OrderMessage orderMessage = this.orderMessageMapper.getOrderMessageById(id);
        NumberMessage numberMessage = this.numberMessageService.getNumberById(orderMessage.getNumberId());
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DATE);
        String dateStr = "" + year;
        if (month < 10){
            dateStr = dateStr + "-0" + month;
        }else{
            dateStr = dateStr + "-" + month;
        }
        if (day < 10){
            dateStr = dateStr + "-0" + day;
        }else{
            dateStr = dateStr + "-" + day;
        }
        Date date = Date.valueOf(dateStr);
        Long dateNow = date.getTime();
        Long dateOrder = numberMessage.getNumberDate().getTime();
//        System.out.println("dateNow: "+dateNow);
//        System.out.println("dateOrder: "+dateOrder);
        if (dateNow >= dateOrder){
            returnMessage.setIsSuccess(1);
            returnMessage.setMessage("就诊当天无法进行退号！");
            return returnMessage;
        }
        if (this.orderMessageMapper.returnNumber(id) >= 1){
            returnMessage.setIsSuccess(0);
            returnMessage.setMessage("修改成功！");
        }else{
            returnMessage.setIsSuccess(1);
            returnMessage.setMessage("发生错误，提交申请失败！");
        }
        return returnMessage;
    }

    @Override
    public int setStatus(int status, int numberId) {
        return this.orderMessageMapper.setStatus(status, numberId);
    }

    @Override
    public List<Map<String, Object>> getCheckList() {
        List<OrderMessage> orderMessageList = this.orderMessageMapper.getCheckList();
        List<Map<String, Object>> resultList = new ArrayList<>();
        int length = orderMessageList.size();
        for (int i = 0; i < length; i++){
            Map<String, Object> result = new HashMap<>();
            OrderMessage orderMessage = orderMessageList.get(i);
            String dept = deptService.getDeptById(orderMessage.getDept()).getName();
            NumberMessage numberMessage = numberMessageService.getNumberById(orderMessage.getNumberId());
            String date = numberMessage.getNumberDate().toString();
            String doctorNum = numberMessage.getDoctorNum();
            String doctorName = doctorService.getDoctorByNum(doctorNum).getName();
            String patientID = orderMessage.getPatientID();
            String patientName = patientService.getPatientByAccount(patientID).getName();
            int orderID = orderMessage.getId();
            int numberID = orderMessage.getNumberId();
            result.put("dept",dept);
            result.put("date", date);
            result.put("doctorNum", doctorNum);
            result.put("doctorName", doctorName);
            result.put("patientID", patientID);
            result.put("patientName", patientName);
            result.put("orderID", orderID);
            result.put("numberID", numberID);
            resultList.add(result);
        }
        return resultList;
    }

    @Override
    public ReturnMessage agreeRetreat(List<Map<String, Integer>> params) {
        int length = params.size();
        System.out.println(params);

        int count = 0;
        ReturnMessage returnMessage = new ReturnMessage();
        for (int i = 0; i < length; i++){
            Map<String, Integer> param = params.get(i);
            System.out.println(param);
            System.out.println(param.get("orderID"));
            System.out.println(param.get("numberID"));
            int orderReturn = this.orderMessageMapper.setStatusById(2, param.get("orderID"));
            int numberReturn = this.numberMessageService.retreat(param.get("numberID"));
            if (orderReturn + numberReturn >= 2){
                count++;
            }
        }
        if (count == length){
            returnMessage.setMessage("退号成功！");
            returnMessage.setIsSuccess(0);
        }else{
            returnMessage.setIsSuccess(1);
            returnMessage.setMessage("退号失败！");
        }
        return returnMessage;
    }

    @Override
    public ReturnMessage disagreeRetreat(int[] params) {
        int length = params.length;
        int count = 0;
        ReturnMessage returnMessage = new ReturnMessage();
        for(int i = 0; i < length; i++){
            if (this.orderMessageMapper.setStatusById(5, params[i]) >= 1){
                count ++;
            }
        }
        if (count == length){
            returnMessage.setMessage("操作成功");
            returnMessage.setIsSuccess(0);
        }else{
            returnMessage.setIsSuccess(1);
            returnMessage.setMessage("操作失败");
        }
        return returnMessage;
    }
}
